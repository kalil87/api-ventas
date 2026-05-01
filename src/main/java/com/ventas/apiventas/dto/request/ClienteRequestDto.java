package com.ventas.apiventas.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDto(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @Email String correo,
        @NotBlank String telefono,
        @NotBlank String direccion
) {}