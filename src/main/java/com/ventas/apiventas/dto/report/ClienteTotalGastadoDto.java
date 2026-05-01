package com.ventas.apiventas.dto.report;

public record ClienteTotalGastadoDto(
        Long clienteId,
        String nombre,
        Double totalGastado
) {}