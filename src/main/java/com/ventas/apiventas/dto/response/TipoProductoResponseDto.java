package com.ventas.apiventas.dto.response;

public record TipoProductoResponseDto(
        Long id,
        String nombre,
        String descripcion
) {}