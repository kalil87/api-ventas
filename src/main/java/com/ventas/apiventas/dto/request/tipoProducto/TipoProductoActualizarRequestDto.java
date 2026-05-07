package com.ventas.apiventas.dto.request.tipoProducto;

import jakarta.validation.constraints.Size;

public record TipoProductoActualizarRequestDto(

        @Size(min = 2, max = 50)
        String nombre,

        @Size(min = 5, max = 255)
        String descripcion
) {}