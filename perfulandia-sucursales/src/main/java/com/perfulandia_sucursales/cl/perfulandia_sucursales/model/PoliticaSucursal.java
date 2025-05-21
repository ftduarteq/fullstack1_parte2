package com.perfulandia_sucursales.cl.perfulandia_sucursales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "politica_sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PoliticaSucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_politica", nullable = false, unique = true)
    private String nombrePolitica;

    @Column(nullable = false)
    private String descripcion;
}
