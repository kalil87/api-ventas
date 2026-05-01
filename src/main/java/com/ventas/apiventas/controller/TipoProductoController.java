package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.request.TipoProductoRequestDto;
import com.ventas.apiventas.dto.response.TipoProductoResponseDto;
import com.ventas.apiventas.service.TipoProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public TipoProductoResponseDto buscarPorId(@PathVariable @Positive(message = "El id debe ser mayor que 0") Long id) {
        return tipoProductoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public TipoProductoResponseDto actualizar(@PathVariable @Positive(message = "El id debe ser mayor que 0") Long id,
                                              @Valid @RequestBody TipoProductoRequestDto dto) {
        return tipoProductoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Positive(message = "El id debe ser mayor que 0") Long id) {
        tipoProductoService.eliminar(id);
    }
}