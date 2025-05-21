package com.perfulandia_inventario.cl.perfulandia_inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
    
    List<Movimiento> findByInventario(Inventario inventario);
}