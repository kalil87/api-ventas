package com.ventas.apiventas.service.impl;

import com.ventas.apiventas.dto.DetalleVentaRequestDto;
import com.ventas.apiventas.dto.VentaRequestDto;
import com.ventas.apiventas.dto.VentaResponseDto;
import com.ventas.apiventas.entity.Cliente;
import com.ventas.apiventas.entity.DetalleVenta;
import com.ventas.apiventas.entity.Producto;
import com.ventas.apiventas.entity.Venta;
import com.ventas.apiventas.mapper.DetalleVentaMapper;
import com.ventas.apiventas.mapper.VentaMapper;
import com.ventas.apiventas.repository.ClienteRepository;
import com.ventas.apiventas.repository.ProductoRepository;
import com.ventas.apiventas.repository.VentaRepository;
import com.ventas.apiventas.service.VentaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    public VentaServiceImpl(ClienteRepository clienteRepository, ProductoRepository productoRepository, VentaRepository ventaRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    /**
     * Crea una nueva venta junto con sus detalles asociados.
     *
     * Flujo:
     * - Busca el cliente
     * - Crea la venta (aún sin ID en BD)
     * - Recorre los detalles:
     *     - Valida existencia de producto
     *     - Valida stock disponible
     *     - Crea el detalle y lo asocia a la venta
     *     - Descuenta stock
     *     - Calcula el total acumulado
     * - Asocia los detalles a la venta
     * - Guarda la venta (cascade persiste también los detalles)
     *
     * Importante:
     * - La venta se usa en memoria antes de persistirse (sin ID aún),
     *   pero JPA maneja las relaciones y asigna el ID automáticamente al hacer save().
     * - @Transactional asegura que si algo falla, no se guarda nada (rollback).
     */
    @Transactional
    public VentaResponseDto crear(VentaRequestDto dto) {

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFechaVenta(LocalDate.now());

        List<DetalleVenta> detalles = new ArrayList<>();
        double total = 0;

        for (DetalleVentaRequestDto detalleVentaRequestDto : dto.detalles()) {

            Producto producto = productoRepository.findById(detalleVentaRequestDto.productoId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe"));

            if (producto.getStock() < detalleVentaRequestDto.cantidad()) {
                throw new RuntimeException("Stock insuficiente, solo quedan " + producto.getStock() + " unidades");
            }

            DetalleVenta detalleVenta = DetalleVentaMapper.toEntity(detalleVentaRequestDto, producto);
            detalleVenta.setVenta(venta);

            producto.setStock(producto.getStock() - detalleVentaRequestDto.cantidad());

            total += detalleVenta.getSubtotal();
            detalles.add(detalleVenta);
        }

        venta.setDetalles(detalles);
        venta.setTotal(total);

        Venta guardada = ventaRepository.save(venta);

        return VentaMapper.toDto(guardada);
    }

    public List<VentaResponseDto> listar() {
        return ventaRepository.findAll()
                .stream()
                .map(VentaMapper::toDto)
                .toList();
    }

    public VentaResponseDto buscarPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        return VentaMapper.toDto(venta);
    }
}