package com.ventas.apiventas.service.impl;

import com.ventas.apiventas.dto.ProductoRequestDto;
import com.ventas.apiventas.dto.ProductoResponseDto;
import com.ventas.apiventas.entity.Producto;
import com.ventas.apiventas.entity.TipoProducto;
import com.ventas.apiventas.mapper.ProductoMapper;
import com.ventas.apiventas.repository.ProductoRepository;
import com.ventas.apiventas.repository.TipoProductoRepository;
import com.ventas.apiventas.service.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, TipoProductoRepository tipoProductoRepository) {
        this.productoRepository = productoRepository;
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public ProductoResponseDto crear(ProductoRequestDto dto) {

        TipoProducto tipo = tipoProductoRepository.findById(dto.tipoProductoId())
                .orElseThrow(() -> new RuntimeException("TipoProducto no encontrado"));

        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setTipoProducto(tipo);

        Producto guardado = productoRepository.save(producto);

        return ProductoMapper.toDto(guardado);
    }

    public List<ProductoResponseDto> listar(String nombre) {
        if (nombre == null) {
            return productoRepository.findAll()
                    .stream()
                    .map(ProductoMapper::toDto)
                    .toList();
        } else {
            return buscarPorNombre(nombre);
        }
    }

    public ProductoResponseDto buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return ProductoMapper.toDto(producto);
    }

    public ProductoResponseDto actualizar(Long id, ProductoRequestDto dto) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        TipoProducto tipo = tipoProductoRepository.findById(dto.tipoProductoId())
                .orElseThrow(() -> new RuntimeException("TipoProducto no encontrado"));

        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setTipoProducto(tipo);

        Producto actualizado = productoRepository.save(producto);

        return ProductoMapper.toDto(actualizado);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public List<ProductoResponseDto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(ProductoMapper::toDto)
                .toList();
    }
}