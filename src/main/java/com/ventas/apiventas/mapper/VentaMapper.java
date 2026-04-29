package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.VentaResponseDto;
import com.ventas.apiventas.entity.Venta;

public class VentaMapper {

    public static VentaResponseDto toDto(Venta venta) {
        return new VentaResponseDto(
                venta.getIdVenta(),
                venta.getCliente().getNombre(),
                venta.getFechaVenta(),
                venta.getTotal(),
                venta.getDetalles()
                        .stream()
                        .map(DetalleVentaMapper::toDto)
                        .toList()
        );
    }
}