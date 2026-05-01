package com.ventas.apiventas.dto.report;

public record ClienteProductoDetalleDto(
        String producto,
        Long cantidadTotal,
        Double totalGastado
) {}