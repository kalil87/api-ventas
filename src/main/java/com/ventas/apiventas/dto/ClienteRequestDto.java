package com.ventas.apiventas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDto(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @Email String correo,
        String telefono,
        String direccion
) {}