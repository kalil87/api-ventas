package com.ventas.apiventas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable=false, length = 100)
    private String nombre;

    @Column(nullable=false, length = 100)
    private String apellido;

    @Column(nullable=false, length = 100)
    private String correo;

    @Column(nullable=false, length = 100)
    private String telefono;

    @Column(nullable=false, length = 100)
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas;
}