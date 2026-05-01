package com.ventas.apiventas.dto.report;

public record VentasPorFechaDto(
        Integer anio,
        Integer mes,
        Integer dia,
        Double total
) {}