package com.ventas.apiventas.dto.request.producto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductoActualizarRequestDto(

        @Size(min = 2, max = 100)
        String nombre,

        @Positive
        @DecimalMin(value = "0.01")
        Double precio,

        @PositiveOrZero
        Integer stock,

        @Positive
        Long tipoProductoId
) {}