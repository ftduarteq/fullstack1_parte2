package com.perfulandia_sucursales.cl.perfulandia_sucursales.repository;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticaSucursalRepository extends JpaRepository<PoliticaSucursal, Integer> {

}
