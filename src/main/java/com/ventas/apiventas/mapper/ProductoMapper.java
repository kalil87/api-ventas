package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.request.producto.ProductoActualizarRequestDto;
import com.ventas.apiventas.dto.request.producto.ProductoCrearRequestDto;
import com.ventas.apiventas.dto.response.ProductoResponseDto;
import com.ventas.apiventas.entity.Producto;
import com.ventas.apiventas.entity.TipoProducto;

import java.util.Optional;

public class ProductoMapper {

    public static Producto toEntity(ProductoCrearRequestDto dto, TipoProducto tipo) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setTipoProducto(tipo);
        return producto;
    }

    public static ProductoResponseDto toDto(Producto producto) {
        return new ProductoResponseDto(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getTipoProducto().getNombre());
    }

    public static void updateEntity(Producto producto, ProductoActualizarRequestDto dto, TipoProducto tipo) {
        Optional.ofNullable(dto.nombre()).ifPresent(producto::setNombre);
        Optional.ofNullable(dto.precio()).ifPresent(producto::setPrecio);
        Optional.ofNullable(dto.stock()).ifPresent(producto::setStock);
        Optional.ofNullable(tipo).ifPresent(producto::setTipoProducto);
    }
}