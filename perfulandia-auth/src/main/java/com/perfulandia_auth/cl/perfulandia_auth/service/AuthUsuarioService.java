package com.perfulandia_auth.cl.perfulandia_auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.repository.AuthUsuarioRepository;
import com.perfulandia_auth.cl.perfulandia_auth.repository.RolUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class AuthUsuarioService {

    @Autowired
    private AuthUsuarioRepository authUsuarioRepository;

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<AuthUsuario> findAll() {
        return authUsuarioRepository.findAll();
    }

    public AuthUsuario findById(Integer id) {
        return authUsuarioRepository.findById(id).get();
    }

    public AuthUsuario save(AuthUsuario authUsuario) {
        
        RolUsuario rol = rolUsuarioRepository.findById(authUsuario.getRolUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        authUsuario.setRolUsuario(rol);
        return authUsuarioRepository.save(authUsuario);
    }

    public void delete(Integer id) {
        authUsuarioRepository.deleteById(id);
    }

}
