package com.ventas.apiventas.service.impl;

import com.ventas.apiventas.dto.request.TipoProductoRequestDto;
import com.ventas.apiventas.dto.response.TipoProductoResponseDto;
import com.ventas.apiventas.entity.TipoProducto;
import com.ventas.apiventas.exception.NoEncontradoException;
import com.ventas.apiventas.mapper.TipoProductoMapper;
import com.ventas.apiventas.repository.TipoProductoRepository;
import com.ventas.apiventas.service.TipoProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {
    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoServiceImpl(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    public TipoProductoResponseDto crear(TipoProductoRequestDto dto) {
        TipoProducto tipo = TipoProductoMapper.toEntity(dto);
        TipoProducto guardado = tipoProductoRepository.save(tipo);
        return TipoProductoMapper.toDto(guardado);
    }

    public List<TipoProductoResponseDto> listar() {
        return tipoProductoRepository.findAll()
                .stream()
                .map(TipoProductoMapper::toDto)
                .toList();
    }

    public TipoProductoResponseDto buscarPorId(Long id) {
        TipoProducto tipo = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("TipoProducto no encontrado"));

        return TipoProductoMapper.toDto(tipo);
    }

    public TipoProductoResponseDto actualizar(Long id, TipoProductoRequestDto dto) {
        TipoProducto tipo = tipoProductoRepository.findById(id)
                .orElseThrow(() -> new NoEncontradoException("TipoProducto no encontrado"));

        tipo.setNombre(dto.nombre());
        tipo.setDescripcion(dto.descripcion());

        TipoProducto actualizado = tipoProductoRepository.save(tipo);
        return TipoProductoMapper.toDto(actualizado);
    }

    public void eliminar(Long id) {
        if (!tipoProductoRepository.existsById(id)) {
            throw new NoEncontradoException("TipoProducto no encontrado");
        }
        tipoProductoRepository.deleteById(id);
    }
}