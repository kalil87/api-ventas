package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.ClienteRequestDto;
import com.ventas.apiventas.dto.ClienteResponseDto;

import java.util.List;

public interface ClienteService {

    ClienteResponseDto crear(ClienteRequestDto dto);

    List<ClienteResponseDto> listar();

    ClienteResponseDto buscarPorId(Long id);

    ClienteResponseDto actualizar(Long id, ClienteRequestDto dto);

    void eliminar(Long id);
}
