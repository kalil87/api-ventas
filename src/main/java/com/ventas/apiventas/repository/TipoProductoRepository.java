package com.ventas.apiventas.repository;

import com.ventas.apiventas.entity.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
}