package com.ventas.apiventas.dto;

import jakarta.validation.constraints.*;

public record ProductoRequestDto(

        @NotBlank
        @Size(max = 100)
        String nombre,

        @NotNull
        @Positive
        Double precio,

        @NotNull
        @PositiveOrZero
        Integer stock,

        @NotNull
        Long tipoProductoId
) {}