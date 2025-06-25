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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/categorias")
@Tag(name = "Categoría de Productos", description = "Operaciones relacionadas con las categorías de productos")
public class CategoriaProductoController {

    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @GetMapping
    @Operation(summary = "Listar todas las categorías", description = "Obtiene una lista de todas las categorías de productos disponibles")
    public ResponseEntity<List<CategoriaProducto>> listar() {
        List<CategoriaProducto> categorias = categoriaProductoService.findAll();
        if(categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar categoría por ID", description = "Busca una categoría de producto según su identificador")
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
    @Operation(summary = "Guardar nueva categoría", description = "Guarda una nueva categoría de producto en el sistema")
    public ResponseEntity<CategoriaProducto> guardar(@RequestBody CategoriaProducto categoria) {
        CategoriaProducto categoriaNueva = categoriaProductoService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaNueva);
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar categoría existente", description = "Actualiza los datos de una categoría de producto existente")
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
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría de producto del sistema por su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            categoriaProductoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
