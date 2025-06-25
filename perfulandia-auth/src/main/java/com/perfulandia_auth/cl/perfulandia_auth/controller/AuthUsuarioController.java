package com.perfulandia_auth.cl.perfulandia_auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.service.AuthUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/authusuarios")
@Tag(name = "Usuarios de Autenticación", description = "Operaciones relacionadas con usuarios para autenticación y autorización")
public class AuthUsuarioController {

    @Autowired
    private AuthUsuarioService authUsuarioService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios de autenticación", description = "Obtiene una lista de todos los usuarios registrados para autenticación")
    public ResponseEntity<List<AuthUsuario>> listar(){
        List<AuthUsuario> authUsuarios = authUsuarioService.findAll();
        if(authUsuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(authUsuarios);
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo usuario de autenticación", description = "Registra un nuevo usuario para autenticación en el sistema")
    public ResponseEntity<AuthUsuario> guardar(@RequestBody AuthUsuario authUsuario){
        AuthUsuario authUsuarioNuevo = authUsuarioService.save(authUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(authUsuarioNuevo);
    }
    
    @GetMapping("{id}")
    @Operation(summary = "Buscar usuario de autenticación por ID", description = "Busca un usuario de autenticación según su identificador")
    public ResponseEntity<AuthUsuario> buscar(@PathVariable Integer id){
        try{
            AuthUsuario authUsuario = authUsuarioService.findById(id);
            return ResponseEntity.ok(authUsuario);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar usuario de autenticación existente", description = "Actualiza la información de un usuario de autenticación")
    public ResponseEntity<AuthUsuario> actualizar(@PathVariable Integer id, @RequestBody AuthUsuario authUsuario){
        try {
            AuthUsuario au = authUsuarioService.findById(id);
            
            au.setId(id);
            au.setUsername(authUsuario.getUsername());
            au.setPassword(authUsuario.getPassword());

            au.setRolUsuario(authUsuario.getRolUsuario());

            authUsuarioService.save(au);
            return ResponseEntity.ok(authUsuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar usuario de autenticación", description = "Elimina un usuario de autenticación del sistema por su identificador")
    public ResponseEntity<AuthUsuario> eliminar(@PathVariable Integer id){
        try {
            authUsuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
