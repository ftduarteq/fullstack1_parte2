package com.perfulandia_auth.cl.perfulandia_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;

public interface AuthUsuarioRepository extends JpaRepository<AuthUsuario, Integer> {

}