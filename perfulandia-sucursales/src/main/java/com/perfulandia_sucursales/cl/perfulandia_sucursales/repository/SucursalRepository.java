package com.perfulandia_sucursales.cl.perfulandia_sucursales.repository;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

}
