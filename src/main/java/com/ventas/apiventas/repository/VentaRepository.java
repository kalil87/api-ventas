package com.ventas.apiventas.repository;

import com.ventas.apiventas.dto.report.ClienteProductoDetalleDto;
import com.ventas.apiventas.dto.report.ClienteTotalGastadoDto;
import com.ventas.apiventas.dto.report.ProductoMasVendidoDto;
import com.ventas.apiventas.dto.report.VentasPorFechaDto;
import com.ventas.apiventas.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("""
    SELECT new com.ventas.apiventas.dto.report.ClienteTotalGastadoDto(
        c.id,
        c.nombre,
        SUM(v.total)
    )
    FROM Venta v
    JOIN v.cliente c
    GROUP BY c.id, c.nombre
    ORDER BY SUM(v.total) DESC
    """)
    List<ClienteTotalGastadoDto> obtenerTotalGastadoDesc();

    @Query("""
    SELECT new com.ventas.apiventas.dto.report.ClienteTotalGastadoDto(
        c.id,
        c.nombre,
        SUM(v.total)
    )
    FROM Venta v
    JOIN v.cliente c
    GROUP BY c.id, c.nombre
    ORDER BY SUM(v.total) ASC
    """)
    List<ClienteTotalGastadoDto> obtenerTotalGastadoAsc();

    @Query("""
SELECT new com.ventas.apiventas.dto.report.ClienteProductoDetalleDto(
    p.nombre,
    SUM(dv.cantidad),
    SUM(dv.subtotal)
)
FROM Venta v
JOIN v.detalles dv
JOIN dv.producto p
WHERE v.cliente.id = :clienteId
GROUP BY p.nombre
""")
    List<ClienteProductoDetalleDto> obtenerDetalleAgrupadoPorCliente(@Param("clienteId") Long clienteId);

    @Query("""
SELECT new com.ventas.apiventas.dto.report.ProductoMasVendidoDto(
    p.nombre,
    SUM(dv.cantidad),
    SUM(dv.subtotal)
)
FROM Venta v
JOIN v.detalles dv
JOIN dv.producto p
GROUP BY p.nombre
ORDER BY SUM(dv.cantidad) DESC
""")
    List<ProductoMasVendidoDto> obtenerProductosMasVendidos();

    @Query("""
SELECT new com.ventas.apiventas.dto.report.VentasPorFechaDto(
    YEAR(v.fechaVenta),
    MONTH(v.fechaVenta),
    DAY(v.fechaVenta),
    SUM(v.total)
)
FROM Venta v
WHERE v.fechaVenta BETWEEN :desde AND :hasta
GROUP BY YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta)
ORDER BY YEAR(v.fechaVenta), MONTH(v.fechaVenta), DAY(v.fechaVenta)
""")
    List<VentasPorFechaDto> ventasPorFecha(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

    @Query("""
SELECT SUM(v.total)
FROM Venta v
WHERE v.fechaVenta BETWEEN :desde AND :hasta
""")
    Double totalPorRango(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);
}

