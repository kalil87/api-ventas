package com.ventas.apiventas.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TipoProductoRequestDto(
        @NotBlank String nombre,
        @NotBlank String descripcion
) {}