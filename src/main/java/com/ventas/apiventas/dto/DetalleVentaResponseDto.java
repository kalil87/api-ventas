package com.ventas.apiventas.dto;

public record DetalleVentaResponseDto(
        Long productoId,
        String productoNombre,
        Integer cantidad,
        Double precioUnitario,
        Double subtotal
) {}