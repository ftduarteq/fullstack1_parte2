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

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.service.BodegaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/bodegas")
@Tag(name = "Bodegas", description = "Operaciones relacionadas con bodegas")
public class BodegaController {

    @Autowired
    private BodegaService bodegaService;

    @GetMapping
    @Operation(summary = "Listar todas las bodegas", description = "Obtiene una lista de todas las bodegas registradas")
    public ResponseEntity<List<Bodega>> listar() {
        List<Bodega> listaBodegas = bodegaService.findAll();
        if (listaBodegas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaBodegas);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar bodega por ID", description = "Busca una bodega por su identificador")
    public ResponseEntity<Bodega> buscar(@PathVariable Integer id) {
        try {
            Bodega bodega = bodegaService.findById(id);
            return ResponseEntity.ok(bodega);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva bodega", description = "Registra una nueva bodega en el sistema")
    public ResponseEntity<Bodega> guardar(@RequestBody Bodega bodega) {
        Bodega nuevaBodega = bodegaService.save(bodega);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBodega);
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar bodega existente", description = "Actualiza los datos de una bodega existente")
    public ResponseEntity<Bodega> actualizar(@PathVariable Integer id, @RequestBody Bodega bodega) {
        try {
            Bodega bodegaExistente = bodegaService.findById(id);

            bodegaExistente.setNombreBodega(bodega.getNombreBodega());
            bodegaExistente.setDireccionBodega(bodega.getDireccionBodega());

            bodegaService.save(bodegaExistente);
            return ResponseEntity.ok(bodegaExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar bodega", description = "Elimina una bodega por su identificador")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            bodegaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
