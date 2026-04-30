package com.ventas.apiventas.service.impl;

import com.ventas.apiventas.dto.ClienteRequestDto;
import com.ventas.apiventas.dto.ClienteResponseDto;
import com.ventas.apiventas.entity.Cliente;
import com.ventas.apiventas.mapper.ClienteMapper;
import com.ventas.apiventas.repository.ClienteRepository;
import com.ventas.apiventas.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return ClienteMapper.toDto(cliente);
    }

    public ClienteResponseDto actualizar(Long id, ClienteRequestDto dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setCorreo(dto.correo());

        Cliente actualizado = clienteRepository.save(cliente);
        return ClienteMapper.toDto(actualizado);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }
}