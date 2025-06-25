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

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.OrigenProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/origenes")
@Tag(name = "Origen de Productos", description = "Operaciones relacionadas con los orígenes de los productos")
public class OrigenProductoController {

    @Autowired
    private OrigenProductoService origenProductoService;

    @GetMapping
    @Operation(summary = "Listar todos los orígenes", description = "Obtiene una lista de todos los orígenes de productos disponibles")
    public ResponseEntity<List<OrigenProducto>> listar() {
        List<OrigenProducto> origenes = origenProductoService.findAll();
        if (origenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(origenes);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar origen por ID", description = "Busca un origen de producto según su identificador")
    public ResponseEntity<OrigenProducto> buscar(@PathVariable Integer id) {
        try {
            OrigenProducto origen = origenProductoService.findById(id);
            return ResponseEntity.ok(origen);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo origen", description = "Guarda un nuevo origen de producto en el sistema")
    public ResponseEntity<OrigenProducto> guardar(@RequestBody OrigenProducto origen) {
        OrigenProducto nuevoOrigen = origenProductoService.save(origen);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoOrigen);
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar origen existente", description = "Actualiza los datos de un origen de producto existente")
    public ResponseEntity<OrigenProducto> actualizar(@PathVariable Integer id, @RequestBody OrigenProducto origen) {
        try {
            OrigenProducto origenExistente = origenProductoService.findById(id);
            origenExistente.setId(id);
            origenExistente.setPais(origen.getPais());
            origenExistente.setRegion(origen.getRegion());

            origenProductoService.save(origenExistente);
            return ResponseEntity.ok(origenExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar origen", description = "Elimina un origen de producto del sistema por su identificador")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            origenProductoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
