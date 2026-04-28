package com.ventas.apiventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tipo_productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoProducto;

    @Column(nullable=false, length = 100)
    private String nombre;

    @Column(nullable=false, length = 100)
    private String descripcion;

    @OneToMany(mappedBy = "tipoProducto")
    private List<Producto> productos;
}