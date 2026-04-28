package com.ventas.apiventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable=false, length = 100)
    private String nombre;

    @Column(nullable=false)
    private Double precio;

    @Column(nullable=false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "id_tipo_producto")
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalles;
}