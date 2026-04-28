package com.ventas.apiventas.repository;

import com.ventas.apiventas.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}