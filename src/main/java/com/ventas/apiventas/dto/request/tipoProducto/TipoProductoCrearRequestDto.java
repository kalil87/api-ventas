package com.ventas.apiventas.dto.request.tipoProducto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TipoProductoCrearRequestDto(

        @NotBlank
        @Size(min = 2, max = 50)
        String nombre,

        @NotBlank
        @Size(min = 5, max = 255)
        String descripcion
) {}