package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.request.cliente.ClienteActualizarRequestDto;
import com.ventas.apiventas.dto.request.cliente.ClienteCrearRequestDto;
import com.ventas.apiventas.dto.response.ClienteResponseDto;

import java.util.List;

public interface ClienteService {

    ClienteResponseDto crear(ClienteCrearRequestDto dto);

    List<ClienteResponseDto> listar();

    ClienteResponseDto buscarPorId(Long id);

    ClienteResponseDto actualizar(Long id, ClienteActualizarRequestDto dto);

    void eliminar(Long id);
}