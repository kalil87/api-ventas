package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.VentaRequestDto;
import com.ventas.apiventas.dto.VentaResponseDto;
import com.ventas.apiventas.service.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public VentaResponseDto buscarPorId(@PathVariable @Positive(message = "El id debe ser mayor que 0") Long id) {
        return ventaService.buscarPorId(id);
    }
}