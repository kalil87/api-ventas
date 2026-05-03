package com.ventas.apiventas.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VentaRequestDto(
        @NotNull
        Long clienteId,

        @NotEmpty
        List<@NotNull DetalleVentaRequestDto> detalles
) {}