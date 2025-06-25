package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.PoliticaSucursalService;

@RestController
@RequestMapping("api/v1/politicas-sucursal")
public class PoliticaSucursalController {

    @Autowired
    private PoliticaSucursalService politicaSucursalService;

    @GetMapping
    public ResponseEntity<List<PoliticaSucursal>> listar() {
        List<PoliticaSucursal> lista = politicaSucursalService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    public ResponseEntity<PoliticaSucursal> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(politicaSucursalService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PoliticaSucursal> guardar(@RequestBody PoliticaSucursal politica) {
        PoliticaSucursal nuevaPolitica = politicaSucursalService.save(politica);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPolitica);
    }

    @PutMapping("{id}")
    public ResponseEntity<PoliticaSucursal> actualizar(@PathVariable Integer id, @RequestBody PoliticaSucursal politica) {
        try {
            PoliticaSucursal politicaExistente = politicaSucursalService.findById(id);
            politicaExistente.setNombrePolitica(politica.getNombrePolitica());
            politicaExistente.setDescripcion(politica.getDescripcion());
            politicaSucursalService.save(politicaExistente);
            return ResponseEntity.ok(politicaExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            politicaSucursalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
