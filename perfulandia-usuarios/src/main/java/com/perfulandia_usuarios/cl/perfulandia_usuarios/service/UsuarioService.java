package com.perfulandia_usuarios.cl.perfulandia_usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.Usuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.CategoriaUsuarioRepository;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaUsuarioRepository categoriaUsuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario save(Usuario usuario) {
        
        CategoriaUsuario cu = categoriaUsuarioRepository.findById(usuario.getCategoriaUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

                usuario.setCategoriaUsuario(cu);
        return usuarioRepository.save(usuario);
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
