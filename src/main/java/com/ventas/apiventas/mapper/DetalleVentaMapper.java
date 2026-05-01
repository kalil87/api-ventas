package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.request.DetalleVentaRequestDto;
import com.ventas.apiventas.dto.response.DetalleVentaResponseDto;
import com.ventas.apiventas.entity.DetalleVenta;
import com.ventas.apiventas.entity.Producto;

public class DetalleVentaMapper {

    public static DetalleVenta toEntity(DetalleVentaRequestDto dto, Producto producto) {
        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setProducto(producto);
        detalleVenta.setCantidad(dto.cantidad());
        detalleVenta.setPrecioUnitario(producto.getPrecio());
        detalleVenta.setSubtotal(producto.getPrecio() * dto.cantidad());
        return detalleVenta;
    }

    public static DetalleVentaResponseDto toDto(DetalleVenta detalleVenta) {
        return new DetalleVentaResponseDto(
                detalleVenta.getProducto().getIdProducto(),
                detalleVenta.getProducto().getNombre(),
                detalleVenta.getCantidad(),
                detalleVenta.getPrecioUnitario(),
                detalleVenta.getSubtotal()
        );
    }
}