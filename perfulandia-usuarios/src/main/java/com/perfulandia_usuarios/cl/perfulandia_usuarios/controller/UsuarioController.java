package com.perfulandia_usuarios.cl.perfulandia_usuarios.controller;

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

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.Usuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.service.UsuarioService;

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Usuario usuario){
        Usuario usuarioNuevo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable Integer id){
        try{
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        try {
            Usuario u = usuarioService.findById(id);
            
            u.setId(id);
            u.setUsername(usuario.getUsername());
            u.setNombres(usuario.getNombres());
            u.setApellidos(usuario.getApellidos());
            u.setRut(usuario.getRut());
            u.setFechaNacimiento(usuario.getFechaNacimiento());
            u.setCategoriaUsuario(usuario.getCategoriaUsuario());

            usuarioService.save(u);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Usuario> eliminar(@PathVariable Integer id){
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
