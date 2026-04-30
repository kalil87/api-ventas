package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.ProductoRequestDto;
import com.ventas.apiventas.dto.ProductoResponseDto;
import com.ventas.apiventas.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponseDto crear(@Valid @RequestBody ProductoRequestDto dto) {
        return productoService.crear(dto);
    }

    @GetMapping
    public List<ProductoResponseDto> listar(@Valid @RequestParam(required = false) String nombre) {
        return productoService.listar(nombre);
    }

    @GetMapping("/{id}")
    public ProductoResponseDto buscarPorId(@Valid @PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProductoResponseDto actualizar(@Valid @PathVariable Long id, @Valid @RequestBody ProductoRequestDto dto) {
        return productoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@Valid @PathVariable Long id) {
        productoService.eliminar(id);
    }
}