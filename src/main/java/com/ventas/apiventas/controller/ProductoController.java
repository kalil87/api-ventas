package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.request.ProductoRequestDto;
import com.ventas.apiventas.dto.response.ProductoResponseDto;
import com.ventas.apiventas.service.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
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
    public List<ProductoResponseDto> listar(@RequestParam(required = false)
                                                @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
                                                String nombre) {
        return productoService.listar(nombre);
    }

    @GetMapping("/{id}")
    public ProductoResponseDto buscarPorId(@PathVariable @Positive(message = "El ID debe ser mayor que 0") Long id) {
        return productoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProductoResponseDto actualizar(@PathVariable @Positive(message = "El ID debe ser mayor que 0") Long id,
                                          @Valid @RequestBody ProductoRequestDto dto) {
        return productoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable @Positive(message = "El ID debe ser mayor que 0") Long id) {
        productoService.eliminar(id);
    }
}