package com.perfulandia_auth.cl.perfulandia_auth.model;

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
@Table(name="auth_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthUsuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, length=20, nullable=false)
    private String username;

    @Column(length=20, nullable=false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false)
    private RolUsuario rolUsuario;
}
