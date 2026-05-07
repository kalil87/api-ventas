package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.request.tipoProducto.TipoProductoActualizarRequestDto;
import com.ventas.apiventas.dto.request.tipoProducto.TipoProductoCrearRequestDto;
import com.ventas.apiventas.dto.response.TipoProductoResponseDto;
import com.ventas.apiventas.entity.TipoProducto;

import java.util.Optional;

public class TipoProductoMapper {

    public static TipoProducto toEntity(TipoProductoCrearRequestDto dto) {
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setNombre(dto.nombre());
        tipoProducto.setDescripcion(dto.descripcion());
        return tipoProducto;
    }

    public static TipoProductoResponseDto toDto(TipoProducto tipoProducto) {
        return new TipoProductoResponseDto(
                tipoProducto.getIdTipoProducto(),
                tipoProducto.getNombre(),
                tipoProducto.getDescripcion()
        );
    }

    public static void updateEntity(TipoProducto tipoProducto, TipoProductoActualizarRequestDto dto) {
//        tipoProducto.setNombre(dto.nombre());
//        tipoProducto.setDescripcion(dto.descripcion());
        Optional.ofNullable(dto.nombre()).ifPresent(tipoProducto::setNombre);
        Optional.ofNullable(dto.descripcion()).ifPresent(tipoProducto::setDescripcion);
    }
}