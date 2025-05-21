package com.perfulandia_sucursales.cl.perfulandia_sucursales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "personal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_empleado", nullable = false, unique = true)
    private String codigoEmpleado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_personal", nullable = false)
    private EstadoPersonal estadoPersonal;

    @ManyToOne
    @JoinColumn(name = "id_cargo", nullable = false)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;
}