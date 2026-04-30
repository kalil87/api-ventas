package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.TipoProductoRequestDto;
import com.ventas.apiventas.dto.TipoProductoResponseDto;
import com.ventas.apiventas.service.TipoProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-producto")
public class TipoProductoController {
    private final TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoProductoResponseDto crear(@Valid @RequestBody TipoProductoRequestDto dto) {
        return tipoProductoService.crear(dto);
    }

    @GetMapping
    public List<TipoProductoResponseDto> listar() {
        return tipoProductoService.listar();
    }

    @GetMapping("/{id}")
    public TipoProductoResponseDto buscarPorId(@Valid @PathVariable Long id) {
        return tipoProductoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public TipoProductoResponseDto actualizar(@Valid @PathVariable Long id, @Valid @RequestBody TipoProductoRequestDto dto) {
        return tipoProductoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@Valid @PathVariable Long id) {
        tipoProductoService.eliminar(id);
    }
}