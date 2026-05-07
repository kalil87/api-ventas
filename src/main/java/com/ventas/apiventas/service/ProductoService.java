package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.request.producto.ProductoActualizarRequestDto;
import com.ventas.apiventas.dto.request.producto.ProductoCrearRequestDto;
import com.ventas.apiventas.dto.response.ProductoResponseDto;

import java.util.List;

public interface ProductoService {

    ProductoResponseDto crear(ProductoCrearRequestDto dto);

    List<ProductoResponseDto> listar(String nombre);

    ProductoResponseDto buscarPorId(Long id);

    ProductoResponseDto actualizar(Long id, ProductoActualizarRequestDto dto);

    void eliminar(Long id);

    List<ProductoResponseDto> buscarPorNombre(String nombre);
}