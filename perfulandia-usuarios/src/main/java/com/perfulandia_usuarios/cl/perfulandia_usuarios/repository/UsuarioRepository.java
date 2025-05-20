package com.perfulandia_usuarios.cl.perfulandia_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
