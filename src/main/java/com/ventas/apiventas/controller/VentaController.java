package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.VentaRequestDto;
import com.ventas.apiventas.dto.VentaResponseDto;
import com.ventas.apiventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public VentaResponseDto buscarPorId(@Valid @PathVariable Long id) {
        return ventaService.buscarPorId(id);
    }
}