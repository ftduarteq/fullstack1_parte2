package com.perfulandia_usuarios.cl.perfulandia_usuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.service.CategoriaUsuarioService;

@RestController
@RequestMapping("api/v1/categoriasusuarios")

public class CategoriaUsuarioController {

    @Autowired
    private CategoriaUsuarioService categoriaUsuarioService;

    @GetMapping
    public ResponseEntity<List<CategoriaUsuario>> listar(){
        List<CategoriaUsuario> categoriasUsuario = categoriaUsuarioService.findAll();
        if(categoriasUsuario.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriasUsuario);
    }

    @PostMapping
    public ResponseEntity<CategoriaUsuario> guardar(@RequestBody CategoriaUsuario categoriaUsuario) {
        CategoriaUsuario categoriaUsuarioNuevo = categoriaUsuarioService.save(categoriaUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaUsuarioNuevo);
    }
}
