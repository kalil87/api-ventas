package com.ventas.apiventas.dto.response;

public record ClienteResponseDto(
        Long id,
        String nombre,
        String apellido,
        String correo,
        String telefono,
        String direccion
) {}