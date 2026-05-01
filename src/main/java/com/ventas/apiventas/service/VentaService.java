package com.ventas.apiventas.service;

import com.ventas.apiventas.dto.report.*;
import com.ventas.apiventas.dto.request.VentaRequestDto;
import com.ventas.apiventas.dto.response.VentaResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface VentaService {

    VentaResponseDto crear(VentaRequestDto dto);

    List<VentaResponseDto> listar();

    VentaResponseDto buscarPorId(Long id);

    List<ClienteTotalGastadoDto> totalGastadoPorCliente(String orden);

    List<ClienteProductoDetalleDto> detalleComprasPorCliente(Long clienteId);

    List<ProductoMasVendidoDto> productosMasVendidos();

    List<VentasPorFechaDto> ventasPorFecha(LocalDate desde, LocalDate hasta);

    VentasResumenDto resumenPorFecha(LocalDate desde, LocalDate hasta);
}
