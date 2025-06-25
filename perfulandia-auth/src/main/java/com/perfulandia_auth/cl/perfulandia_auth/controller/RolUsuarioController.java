package com.perfulandia_auth.cl.perfulandia_auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.service.RolUsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/rolesusuarios")

public class RolUsuarioController {

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @GetMapping
    public ResponseEntity<List<RolUsuario>> listar(){
        List<RolUsuario> rolesUsuarios = rolUsuarioService.findAll();
        if(rolesUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rolesUsuarios);
    }

    @PostMapping
    public ResponseEntity<RolUsuario> guardar(@RequestBody RolUsuario rolUsuario) {
        RolUsuario rolUsuarioNuevo = rolUsuarioService.save(rolUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolUsuarioNuevo);
    }
    
}
