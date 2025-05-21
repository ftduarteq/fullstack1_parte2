package com.perfulandia_inventario.cl.perfulandia_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.BodegaRepository;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.InventarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private BodegaRepository bodegaRepository;

    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    public Inventario findById(Integer id) {
        return inventarioRepository.findById(id).get();
    }

    public Inventario save(Inventario inventario) {
        Bodega bodega = bodegaRepository.findById(inventario.getBodega().getId())
                .orElseThrow(() -> new RuntimeException("Bodega no encontrada"));
        inventario.setBodega(bodega);
        return inventarioRepository.save(inventario);
    }

    public void deleteById(Integer id) {
        inventarioRepository.deleteById(id);
    }
}
