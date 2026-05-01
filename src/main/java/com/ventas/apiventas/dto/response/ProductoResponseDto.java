package com.ventas.apiventas.dto.response;

public record ProductoResponseDto(
        Long id,
        String nombre,
        Double precio,
        Integer stock,
        String tipoProducto
) {}