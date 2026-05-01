package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.request.ProductoRequestDto;
import com.ventas.apiventas.dto.response.ProductoResponseDto;

import java.util.List;

public interface ProductoService {

    ProductoResponseDto crear(ProductoRequestDto dto);

    List<ProductoResponseDto> listar(String nombre);

    ProductoResponseDto buscarPorId(Long id);

    ProductoResponseDto actualizar(Long id, ProductoRequestDto dto);

    void eliminar(Long id);

    List<ProductoResponseDto> buscarPorNombre(String nombre);
}
