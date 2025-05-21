package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.CategoriaProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaProductoService {

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    public List<CategoriaProducto> findAll() {
        return categoriaProductoRepository.findAll();
    }

    public CategoriaProducto findById(Integer id) {
        return categoriaProductoRepository.findById(id).get();
    }

    public CategoriaProducto save(CategoriaProducto categoriaProducto) {
        return categoriaProductoRepository.save(categoriaProducto);
    }

    public void deleteById(Integer id) {
        categoriaProductoRepository.deleteById(id);
    }
}
