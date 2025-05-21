package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.PoliticaSucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PoliticaSucursalService {

    @Autowired
    private PoliticaSucursalRepository politicaSucursalRepository;

    public List<PoliticaSucursal> findAll() {
        return politicaSucursalRepository.findAll();
    }

    public PoliticaSucursal findById(Integer id) {
        return politicaSucursalRepository.findById(id).get();
    }

    public PoliticaSucursal save(PoliticaSucursal politicaSucursal) {
        return politicaSucursalRepository.save(politicaSucursal);
    }

    public void deleteById(Integer id) {
        politicaSucursalRepository.deleteById(id);
    }
}
