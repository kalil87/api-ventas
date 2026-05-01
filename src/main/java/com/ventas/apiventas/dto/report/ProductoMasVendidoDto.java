package com.ventas.apiventas.dto.report;

public record ProductoMasVendidoDto(
        String producto,
        Long cantidadVendida,
        Double totalIngresado
) {}