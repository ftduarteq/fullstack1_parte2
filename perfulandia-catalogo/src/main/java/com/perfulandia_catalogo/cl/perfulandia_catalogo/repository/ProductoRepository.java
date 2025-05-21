package com.perfulandia_catalogo.cl.perfulandia_catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
