package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.OrigenProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrigenProductoService {

    @Autowired
    private OrigenProductoRepository origenProductoRepository;

    public List<OrigenProducto> findAll() {
        return origenProductoRepository.findAll();
    }

    public OrigenProducto findById(Integer id) {
        return origenProductoRepository.findById(id).get();
    }

    public OrigenProducto save(OrigenProducto origenProducto) {
        return origenProductoRepository.save(origenProducto);
    }

    public void deleteById(Integer id) {
        origenProductoRepository.deleteById(id);
    }
}
