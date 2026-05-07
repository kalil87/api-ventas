package com.ventas.apiventas.dto.request.producto;

import jakarta.validation.constraints.*;

public record ProductoCrearRequestDto(

        @NotBlank
        @Size(min = 2, max = 100)
        String nombre,

        @NotNull
        @Positive
        @DecimalMin(value = "0.01")
        Double precio,

        @NotNull
        @PositiveOrZero
        Integer stock,

        @NotNull
        @Positive
        Long tipoProductoId
) {}