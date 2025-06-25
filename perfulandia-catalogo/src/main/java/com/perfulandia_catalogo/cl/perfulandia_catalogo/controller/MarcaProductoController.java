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

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.MarcaProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/marcas")
@Tag(name = "Marca de Productos", description = "Operaciones relacionadas con marcas de productos")
public class MarcaProductoController {

    @Autowired
    private MarcaProductoService marcaProductoService;

    @GetMapping
    @Operation(summary = "Listar todas las marcas", description = "Obtiene una lista de todas las marcas de productos disponibles")
    public ResponseEntity<List<MarcaProducto>> listar() {
        List<MarcaProducto> marcas = marcaProductoService.findAll();
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(marcas);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar marca por ID", description = "Obtiene una marca de producto según su identificador")
    public ResponseEntity<MarcaProducto> buscar(@PathVariable Integer id) {
        try {
            MarcaProducto marca = marcaProductoService.findById(id);
            return ResponseEntity.ok(marca);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva marca", description = "Registra una nueva marca de producto")
    public ResponseEntity<MarcaProducto> guardar(@RequestBody MarcaProducto marca) {
        MarcaProducto marcaNueva = marcaProductoService.save(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaNueva);
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar marca existente", description = "Actualiza los datos de una marca de producto existente")
    public ResponseEntity<MarcaProducto> actualizar(@PathVariable Integer id, @RequestBody MarcaProducto marca) {
        try {
            MarcaProducto marcaExistente = marcaProductoService.findById(id);
            marcaExistente.setId(id);
            marcaExistente.setNombre(marca.getNombre());

            marcaProductoService.save(marcaExistente);
            return ResponseEntity.ok(marcaExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar marca", description = "Elimina una marca de producto según su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            marcaProductoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
