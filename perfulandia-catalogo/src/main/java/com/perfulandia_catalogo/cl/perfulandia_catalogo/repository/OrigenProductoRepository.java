package com.perfulandia_catalogo.cl.perfulandia_catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;

@Repository
public interface OrigenProductoRepository extends JpaRepository<OrigenProducto, Integer> {

}
