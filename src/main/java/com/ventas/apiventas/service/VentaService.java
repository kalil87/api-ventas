package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.VentaRequestDto;
import com.ventas.apiventas.dto.VentaResponseDto;

import java.util.List;

public interface VentaService {

    VentaResponseDto crear(VentaRequestDto dto);

    List<VentaResponseDto> listar();

    VentaResponseDto buscarPorId(Long id);
}
