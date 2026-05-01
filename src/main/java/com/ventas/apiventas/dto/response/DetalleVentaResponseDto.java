package com.ventas.apiventas.dto.response;

public record DetalleVentaResponseDto(
        Long productoId,
        String productoNombre,
        Integer cantidad,
        Double precioUnitario,
        Double subtotal
) {}