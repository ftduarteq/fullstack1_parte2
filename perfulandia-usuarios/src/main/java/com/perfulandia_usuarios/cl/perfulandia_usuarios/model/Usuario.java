package com.perfulandia_usuarios.cl.perfulandia_usuarios.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, length=20, nullable=false)
    private String username;

    @Column(length=20, nullable=false)
    private String nombres;

    @Column(length=20, nullable=false)
    private String apellidos;

    @Column(length=10, nullable=false)
    private String rut;

    @Column(nullable=false)
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_categoria",nullable = false)
    private CategoriaUsuario categoriaUsuario;
}
