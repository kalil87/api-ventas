package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.request.cliente.ClienteActualizarRequestDto;
import com.ventas.apiventas.dto.request.cliente.ClienteCrearRequestDto;
import com.ventas.apiventas.dto.response.ClienteResponseDto;
import com.ventas.apiventas.entity.Cliente;

import java.util.Optional;

public class ClienteMapper {

    public static Cliente toEntity(ClienteCrearRequestDto dto) {
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
                cliente.getDireccion());
    }

    public static void updateEntity(Cliente cliente, ClienteActualizarRequestDto dto) {
        Optional.ofNullable(dto.nombre()).ifPresent(cliente::setNombre);
        Optional.ofNullable(dto.apellido()).ifPresent(cliente::setApellido);
        Optional.ofNullable(dto.correo()).ifPresent(cliente::setCorreo);
        Optional.ofNullable(dto.telefono()).ifPresent(cliente::setTelefono);
        Optional.ofNullable(dto.direccion()).ifPresent(cliente::setDireccion);
    }
}