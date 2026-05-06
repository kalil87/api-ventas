package com.ventas.apiventas.service.impl;

import com.ventas.apiventas.dto.request.ClienteActualizarRequestDto;
import com.ventas.apiventas.dto.request.ClienteRequestDto;
import com.ventas.apiventas.dto.response.ClienteResponseDto;
import com.ventas.apiventas.entity.Cliente;
import com.ventas.apiventas.exception.NoEncontradoException;
import com.ventas.apiventas.mapper.ClienteMapper;
import com.ventas.apiventas.repository.ClienteRepository;
import com.ventas.apiventas.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ClienteResponseDto crear(ClienteRequestDto dto) {

        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente guardado = clienteRepository.save(cliente);
        return ClienteMapper.toDto(guardado);
    }

    public List<ClienteResponseDto> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toDto)
                .toList();
    }

    public ClienteResponseDto buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Cliente no registrado"));

        return ClienteMapper.toDto(cliente);
    }

    public ClienteResponseDto actualizar(Long id, ClienteActualizarRequestDto dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("Cliente no registrado"));

        Optional.ofNullable(dto.nombre()).ifPresent(cliente::setNombre);
        Optional.ofNullable(dto.apellido()).ifPresent(cliente::setApellido);
        Optional.ofNullable(dto.correo()).ifPresent(cliente::setCorreo);
        Optional.ofNullable(dto.telefono()).ifPresent(cliente::setTelefono);
        Optional.ofNullable(dto.direccion()).ifPresent(cliente::setDireccion);

        Cliente actualizado = clienteRepository.save(cliente);
        return ClienteMapper.toDto(actualizado);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NoEncontradoException("Cliente no registrado");
        }
        clienteRepository.deleteById(id);
    }
}