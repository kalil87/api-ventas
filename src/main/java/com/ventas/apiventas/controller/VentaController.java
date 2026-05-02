package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.report.*;
import com.ventas.apiventas.dto.request.VentaRequestDto;
import com.ventas.apiventas.dto.response.VentaResponseDto;
import com.ventas.apiventas.service.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponseDto crear(@Valid @RequestBody VentaRequestDto dto) {
        return ventaService.crear(dto);
    }

    @GetMapping
    public List<VentaResponseDto> listar() {
        return ventaService.listar();
    }

    @GetMapping("/{id}")
    public VentaResponseDto buscarPorId(@PathVariable @Positive(message = "El id debe ser un numero mayor que 0") Long id) {
        return ventaService.buscarPorId(id);
    }

    @GetMapping("/clientes/total-gastado")
    public List<ClienteTotalGastadoDto> totalGastadoPorCliente(@RequestParam(defaultValue = "desc") String orden) {
        return ventaService.totalGastadoPorCliente(orden);
    }

    @GetMapping("/clientes/{id}/compras-detalle")
    public List<ClienteProductoDetalleDto> detalleComprasPorCliente(@PathVariable
                                                                        @Positive(message = "El id debe ser mayor que 0")
                                                                        Long id) {
        return ventaService.detalleComprasPorCliente(id);
    }

    @GetMapping("/productos/top-vendidos")
    public List<ProductoMasVendidoDto> productosMasVendidos() {
        return ventaService.productosMasVendidos();
    }

    @GetMapping("/por-fecha")
    public List<VentasPorFechaDto> ventasPorFecha(@RequestParam LocalDate desde, @RequestParam LocalDate hasta) {
        return ventaService.ventasPorFecha(desde, hasta);
    }

    @GetMapping("/por-fecha/resumen")
    public VentasResumenDto resumen(@RequestParam(required = false) LocalDate desde,
                                    @RequestParam(required = false) LocalDate hasta) {
        return ventaService.resumenPorFecha(desde, hasta);
    }
}