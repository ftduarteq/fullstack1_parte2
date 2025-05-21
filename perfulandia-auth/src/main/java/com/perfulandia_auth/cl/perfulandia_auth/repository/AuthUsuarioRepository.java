package com.perfulandia_auth.cl.perfulandia_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;
@Repository
public interface AuthUsuarioRepository extends JpaRepository<AuthUsuario, Integer> {

}