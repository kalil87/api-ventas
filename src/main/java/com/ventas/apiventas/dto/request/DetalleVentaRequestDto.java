package com.ventas.apiventas.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleVentaRequestDto(
        @NotNull Long productoId,
        @NotNull @Positive Integer cantidad
) {}