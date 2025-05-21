package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Sucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.PoliticaSucursalRepository;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.SucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private PoliticaSucursalRepository politicaSucursalRepository;

    public List<Sucursal> findAll() {
        return sucursalRepository.findAll();
    }

    public Sucursal findById(Integer id) {
        return sucursalRepository.findById(id).get();
    }

    public Sucursal save(Sucursal sucursal) {
        PoliticaSucursal politica = politicaSucursalRepository.findById(sucursal.getPoliticaSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Política de sucursal no encontrada"));

        sucursal.setPoliticaSucursal(politica);
        return sucursalRepository.save(sucursal);
    }

    public void deleteById(Integer id) {
        sucursalRepository.deleteById(id);
    }
}
