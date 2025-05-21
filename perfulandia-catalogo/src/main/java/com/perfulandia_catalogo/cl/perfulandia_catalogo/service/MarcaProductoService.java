package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.MarcaProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MarcaProductoService {

    @Autowired
    private MarcaProductoRepository marcaProductoRepository;

    public List<MarcaProducto> findAll() {
        return marcaProductoRepository.findAll();
    }

    public MarcaProducto findById(Integer id) {
        return marcaProductoRepository.findById(id).get();
    }

    public MarcaProducto save(MarcaProducto marcaProducto) {
        return marcaProductoRepository.save(marcaProducto);
    }

    public void deleteById(Integer id) {
        marcaProductoRepository.deleteById(id);
    }
}
