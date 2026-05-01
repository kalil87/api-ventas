package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.request.TipoProductoRequestDto;
import com.ventas.apiventas.dto.response.TipoProductoResponseDto;
import com.ventas.apiventas.entity.TipoProducto;

public class TipoProductoMapper {

    public static TipoProducto toEntity(TipoProductoRequestDto dto) {
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

    public static void updateEntity(TipoProducto tipoProducto, TipoProductoRequestDto dto) {
        tipoProducto.setNombre(dto.nombre());
        tipoProducto.setDescripcion(dto.descripcion());
    }
}