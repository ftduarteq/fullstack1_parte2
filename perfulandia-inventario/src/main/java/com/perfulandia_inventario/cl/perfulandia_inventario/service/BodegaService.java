package com.perfulandia_inventario.cl.perfulandia_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.BodegaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BodegaService {

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Bodega> findAll() {
        return bodegaRepository.findAll();
    }

    public Bodega findById(Integer id) {
        return bodegaRepository.findById(id).get();
    }

    public Bodega save(Bodega bodega) {
        return bodegaRepository.save(bodega);
    }

    public void deleteById(Integer id) {
        bodegaRepository.deleteById(id);
    }
}