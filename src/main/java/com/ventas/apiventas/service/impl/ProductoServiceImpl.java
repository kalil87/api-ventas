package com.ventas.apiventas.service.impl;

import com.ventas.apiventas.dto.request.producto.ProductoActualizarRequestDto;
import com.ventas.apiventas.dto.request.producto.ProductoCrearRequestDto;
import com.ventas.apiventas.dto.response.ProductoResponseDto;
import com.ventas.apiventas.entity.Producto;
import com.ventas.apiventas.entity.TipoProducto;
import com.ventas.apiventas.exception.NoEncontradoException;
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

    public ProductoResponseDto crear(ProductoCrearRequestDto dto) {

        TipoProducto tipo = tipoProductoRepository.findById(dto.tipoProductoId())
                .orElseThrow(() -> new NoEncontradoException("TipoProducto no encontrado"));

        Producto producto = ProductoMapper.toEntity(dto, tipo);
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
                .orElseThrow(() -> new NoEncontradoException("Producto no encontrado"));

        return ProductoMapper.toDto(producto);
    }

    public ProductoResponseDto actualizar(Long id, ProductoActualizarRequestDto dto) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Producto no encontrado"));

        TipoProducto tipo = obtenerTipoProducto(dto.tipoProductoId());

        ProductoMapper.updateEntity(producto, dto, tipo);

        Producto actualizado = productoRepository.save(producto);

        return ProductoMapper.toDto(actualizado);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(()-> new NoEncontradoException("Producto no registrado"));
        productoRepository.delete(producto);
    }

    public List<ProductoResponseDto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(ProductoMapper::toDto)
                .toList();
    }

    private TipoProducto obtenerTipoProducto(Long tipoProductoId) {

        if (tipoProductoId == null) {
            return null;
        }

        return tipoProductoRepository.findById(tipoProductoId)
                .orElseThrow(() -> new NoEncontradoException("TipoProducto no encontrado"));
    }
}