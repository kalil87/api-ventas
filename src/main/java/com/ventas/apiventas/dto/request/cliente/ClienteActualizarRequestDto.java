package com.ventas.apiventas.dto.request.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteActualizarRequestDto(

        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String nombre,

        @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
        String apellido,

        @Email(message = "El correo debe tener un formato válido")
        String correo,

        @Pattern(regexp = "^[0-9+\\-() ]{6,20}$", message = "El teléfono tiene un formato inválido")
        String telefono,

        @Size(min = 5, max = 100, message = "La dirección debe tener entre 5 y 100 caracteres")
        String direccion
) {}