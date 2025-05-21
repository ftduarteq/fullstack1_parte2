package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Cargo;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Personal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Sucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.CargoRepository;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.PersonalRepository;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.SucursalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonalService {

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    public Personal findById(Integer id) {
        return personalRepository.findById(id).get();
    }

    public Personal save(Personal personal) {
        Cargo cargo = cargoRepository.findById(personal.getCargo().getId())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        Sucursal sucursal = sucursalRepository.findById(personal.getSucursal().getId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        personal.setCargo(cargo);
        personal.setSucursal(sucursal);

        return personalRepository.save(personal);
    }

    public void deleteById(Integer id) {
        personalRepository.deleteById(id);
    }
}
