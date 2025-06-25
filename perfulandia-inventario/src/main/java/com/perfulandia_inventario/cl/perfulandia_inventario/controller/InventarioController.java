package com.perfulandia_inventario.cl.perfulandia_inventario.controller;

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

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario de productos")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Listar inventario completo", description = "Obtiene una lista de todos los registros de inventario")
    public ResponseEntity<List<Inventario>> listar() {
        List<Inventario> listaInventario = inventarioService.findAll();
        if (listaInventario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaInventario);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar inventario por ID", description = "Busca un registro de inventario según su identificador")
    public ResponseEntity<Inventario> buscar(@PathVariable Integer id) {
        try {
            Inventario inventario = inventarioService.findById(id);
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo inventario", description = "Agrega un nuevo registro de inventario")
    public ResponseEntity<Inventario> guardar(@RequestBody Inventario inventario) {
        try {
            Inventario nuevoInventario = inventarioService.save(inventario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInventario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar inventario existente", description = "Actualiza un registro de inventario existente")
    public ResponseEntity<Inventario> actualizar(@PathVariable Integer id, @RequestBody Inventario inventario) {
        try {
            Inventario inventarioExistente = inventarioService.findById(id);

            inventarioExistente.setSkuProducto(inventario.getSkuProducto());
            inventarioExistente.setNombreProducto(inventario.getNombreProducto());
            inventarioExistente.setStockProducto(inventario.getStockProducto());

            inventarioExistente.setBodega(inventario.getBodega());

            inventarioService.save(inventarioExistente);
            return ResponseEntity.ok(inventarioExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un registro de inventario por su identificador")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            inventarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
