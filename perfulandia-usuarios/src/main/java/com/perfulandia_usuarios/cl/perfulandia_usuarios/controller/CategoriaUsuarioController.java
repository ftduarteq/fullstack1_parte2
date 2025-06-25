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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/categoriasusuarios")
@Tag(name = "CategoriasUsuario", description = "Operaciones relacionadas con categorías de usuario")
public class CategoriaUsuarioController {

    @Autowired
    private CategoriaUsuarioService categoriaUsuarioService;

    @GetMapping
    @Operation(summary = "Listar categorías de usuario", description = "Obtiene una lista de todas las categorías de usuario registradas")
    public ResponseEntity<List<CategoriaUsuario>> listar(){
        List<CategoriaUsuario> categoriasUsuario = categoriaUsuarioService.findAll();
        if(categoriasUsuario.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoriasUsuario);
    }

    @PostMapping
    @Operation(summary = "Guardar nueva categoría de usuario", description = "Registra una nueva categoría de usuario")
    public ResponseEntity<CategoriaUsuario> guardar(@RequestBody CategoriaUsuario categoriaUsuario) {
        CategoriaUsuario categoriaUsuarioNuevo = categoriaUsuarioService.save(categoriaUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaUsuarioNuevo);
    }
}
