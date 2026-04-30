package com.ventas.apiventas.controller;

import com.ventas.apiventas.dto.ClienteRequestDto;
import com.ventas.apiventas.dto.ClienteResponseDto;
import com.ventas.apiventas.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDto crear(@Valid @RequestBody ClienteRequestDto dto) {
        return clienteService.crear(dto);
    }

    @GetMapping
    public List<ClienteResponseDto> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteResponseDto buscar(@Valid @PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDto actualizar(@Valid @PathVariable Long id, @Valid @RequestBody ClienteRequestDto dto) {
        return clienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@Valid @PathVariable Long id) {
        clienteService.eliminar(id);
    }
}