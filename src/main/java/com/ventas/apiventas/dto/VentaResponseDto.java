package com.ventas.apiventas.dto;

import java.time.LocalDate;
import java.util.List;

public record VentaResponseDto(
        Long id,
        String clienteNombre,
        LocalDate fechaVenta,
        Double total,
        List<DetalleVentaResponseDto> detalles
) {}