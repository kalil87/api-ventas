package com.ventas.apiventas.mapper;

import com.ventas.apiventas.dto.ProductoRequestDto;
import com.ventas.apiventas.dto.ProductoResponseDto;
import com.ventas.apiventas.entity.Producto;
import com.ventas.apiventas.entity.TipoProducto;

public class ProductoMapper {

    public static Producto toEntity(ProductoRequestDto dto, TipoProducto tipo) {
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
                producto.getTipoProducto().getNombre()
        );
    }

    public static void updateEntity(Producto producto, ProductoRequestDto dto, TipoProducto tipo) {
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setTipoProducto(tipo);
    }
}