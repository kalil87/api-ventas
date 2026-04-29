package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.ClienteRequestDto;
import com.ventas.apiventas.dto.ClienteResponseDto;
import com.ventas.apiventas.entity.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setCorreo(dto.correo());
        cliente.setTelefono(dto.telefono());
        cliente.setDireccion(dto.direccion());
        return cliente;
    }

    public static ClienteResponseDto toDto(Cliente cliente) {
        return new ClienteResponseDto(
                cliente.getIdCliente(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getDireccion()
        );
    }

    public static void updateEntity(Cliente cliente, ClienteRequestDto dto) {
        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setCorreo(dto.correo());
        cliente.setTelefono(dto.telefono());
        cliente.setDireccion(dto.direccion());
    }
}