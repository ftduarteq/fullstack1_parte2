package com.perfulandia_auth.cl.perfulandia_auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.service.RolUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/v1/rolesusuarios")
@Tag(name = "Roles de Usuario", description = "Operaciones relacionadas con los roles de usuarios")
public class RolUsuarioController {

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @GetMapping
    @Operation(summary = "Listar todos los roles de usuario", description = "Obtiene una lista de todos los roles de usuario disponibles")
    public ResponseEntity<List<RolUsuario>> listar(){
        List<RolUsuario> rolesUsuarios = rolUsuarioService.findAll();
        if(rolesUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rolesUsuarios);
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo rol de usuario", description = "Crea un nuevo rol para los usuarios")
    public ResponseEntity<RolUsuario> guardar(@RequestBody RolUsuario rolUsuario) {
        RolUsuario rolUsuarioNuevo = rolUsuarioService.save(rolUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolUsuarioNuevo);
    }
    
}
