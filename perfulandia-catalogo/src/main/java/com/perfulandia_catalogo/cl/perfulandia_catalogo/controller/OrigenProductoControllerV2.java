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

@RestController
@RequestMapping("api/v1/origenes")
public class OrigenProductoController {

    @Autowired
    private OrigenProductoService origenProductoService;

    @GetMapping
    public ResponseEntity<List<OrigenProducto>> listar() {
        List<OrigenProducto> origenes = origenProductoService.findAll();
        if (origenes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(origenes);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrigenProducto> buscar(@PathVariable Integer id) {
        try {
            OrigenProducto origen = origenProductoService.findById(id);
            return ResponseEntity.ok(origen);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrigenProducto> guardar(@RequestBody OrigenProducto origen) {
        OrigenProducto nuevoOrigen = origenProductoService.save(origen);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoOrigen);
    }

    @PutMapping("{id}")
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
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            origenProductoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}