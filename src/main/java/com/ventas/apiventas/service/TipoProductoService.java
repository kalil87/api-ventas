package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.request.tipoProducto.TipoProductoActualizarRequestDto;
import com.ventas.apiventas.dto.request.tipoProducto.TipoProductoCrearRequestDto;
import com.ventas.apiventas.dto.response.TipoProductoResponseDto;

import java.util.List;

public interface TipoProductoService {

    TipoProductoResponseDto crear(TipoProductoCrearRequestDto dto);

    List<TipoProductoResponseDto> listar();

    TipoProductoResponseDto buscarPorId(Long id);

    TipoProductoResponseDto actualizar(Long id, TipoProductoActualizarRequestDto dto);

    void eliminar(Long id);
}