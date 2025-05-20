package com.perfulandia_usuarios.cl.perfulandia_usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.CategoriaUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class CategoriaUsuarioService {
    @Autowired
    private CategoriaUsuarioRepository categoriaUsuarioRepository;

    public List<CategoriaUsuario> findAll() {
        return categoriaUsuarioRepository.findAll();
    }

    public CategoriaUsuario findById(Integer id) {
        return categoriaUsuarioRepository.findById(id).get();
    }

    public CategoriaUsuario save(CategoriaUsuario categoriaUsuario) {
        return categoriaUsuarioRepository.save(categoriaUsuario);
    }

    public void delete(Integer id) {
        categoriaUsuarioRepository.deleteById(id);
    }
}
