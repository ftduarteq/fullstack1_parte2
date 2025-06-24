package com.perfulandia_usuarios.cl.perfulandia_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;

@Repository
public interface CategoriaUsuarioRepository extends JpaRepository<CategoriaUsuario, Integer> {
    CategoriaUsuario findByCategoria(String categoria);
}
