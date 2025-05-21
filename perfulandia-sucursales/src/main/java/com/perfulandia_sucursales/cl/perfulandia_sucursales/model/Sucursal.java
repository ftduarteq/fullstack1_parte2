package com.perfulandia_sucursales.cl.perfulandia_sucursales.model;

import java.time.LocalTime;

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
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_sucursal", nullable = false, unique = true)
    private String nombreSucursal;

    @Column(name = "direccion_sucursal", nullable = false)
    private String direccionSucursal;

    @Column(nullable = false)
    private LocalTime apertura;

    @Column(nullable = false)
    private LocalTime cierre;

    @ManyToOne
    @JoinColumn(name = "id_politica_sucursal", nullable = false)
    private PoliticaSucursal politicaSucursal;
}
