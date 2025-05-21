package com.perfulandia_catalogo.cl.perfulandia_catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;

@Repository
public interface MarcaProductoRepository extends JpaRepository<MarcaProducto, Integer> {

}
