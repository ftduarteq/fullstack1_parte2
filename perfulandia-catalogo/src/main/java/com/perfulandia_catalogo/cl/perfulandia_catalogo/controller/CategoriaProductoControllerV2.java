package com.perfulandia_catalogo.cl.perfulandia_catalogo.controller;

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

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.CategoriaProductoService;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaProductoController {

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> listar() {
        List<CategoriaProducto> categorias = categoriaProductoService.findAll();
        if(categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoriaProducto> buscar(@PathVariable Integer id) {
        try{
            CategoriaProducto categoria = categoriaProductoService.findById(id);
            return ResponseEntity.ok(categoria);
        }
        catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CategoriaProducto> guardar(@RequestBody CategoriaProducto categoria) {
        CategoriaProducto categoriaNueva = categoriaProductoService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaNueva);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoriaProducto> actualizar(@PathVariable Integer id, @RequestBody CategoriaProducto categoria) {
        try {
            CategoriaProducto catProd = categoriaProductoService.findById(id);
            catProd.setId(id);
            catProd.setNombre(categoria.getNombre());

            categoriaProductoService.save(catProd);
            return ResponseEntity.ok(catProd);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            categoriaProductoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}