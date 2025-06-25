package com.perfulandia_inventario.cl.perfulandia_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Movimiento;
import com.perfulandia_inventario.cl.perfulandia_inventario.service.MovimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/movimientos")
@Tag(name = "Movimientos", description = "Operaciones relacionadas con movimientos de inventario")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    @Operation(summary = "Listar todos los movimientos", description = "Obtiene una lista de todos los movimientos de inventario")
    public ResponseEntity<List<Movimiento>> listar() {
        List<Movimiento> listaMovimientos = movimientoService.findAll();
        if (listaMovimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaMovimientos);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar movimiento por ID", description = "Busca un movimiento de inventario según su identificador")
    public ResponseEntity<Movimiento> buscar(@PathVariable Integer id) {
        try {
            Movimiento movimiento = movimientoService.findById(id);
            return ResponseEntity.ok(movimiento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo movimiento", description = "Registra un nuevo movimiento de inventario")
    public ResponseEntity<?> guardar(@RequestBody Movimiento movimiento) {
        try {
            Movimiento nuevoMovimiento = movimientoService.save(movimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMovimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar movimiento", description = "Elimina un movimiento de inventario por su identificador")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            movimientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
