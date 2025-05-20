package com.perfulandia_auth.cl.perfulandia_auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.repository.RolUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class RolUsuarioService {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> findAll() {
        return rolUsuarioRepository.findAll();
    }

    public RolUsuario findById(Integer id) {
        return rolUsuarioRepository.findById(id).get();
    }

    public RolUsuario save(RolUsuario rolUsuario) {
        return rolUsuarioRepository.save(rolUsuario);
    }

    public void delete(Integer id) {
        rolUsuarioRepository.deleteById(id);
    }

}
