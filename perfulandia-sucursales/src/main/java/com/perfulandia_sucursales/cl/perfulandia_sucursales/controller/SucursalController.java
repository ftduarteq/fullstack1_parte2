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


import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Sucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.SucursalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/sucursales")
@Tag(name = "Sucursales", description = "Operaciones relacionadas con sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    @Operation(summary = "Listar todas las sucursales", description = "Obtiene una lista de todas las sucursales registradas")
    public ResponseEntity<List<Sucursal>> listar() {
        List<Sucursal> lista = sucursalService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar sucursal por ID", description = "Busca una sucursal por su identificador")
    public ResponseEntity<Sucursal> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(sucursalService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nueva sucursal", description = "Registra una nueva sucursal")
    public ResponseEntity<Sucursal> guardar(@RequestBody Sucursal sucursal) {
        try {
            Sucursal nuevaSucursal = sucursalService.save(sucursal);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSucursal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar sucursal existente", description = "Actualiza una sucursal registrada")
    public ResponseEntity<Sucursal> actualizar(@PathVariable Integer id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalExistente = sucursalService.findById(id);
            sucursalExistente.setNombreSucursal(sucursal.getNombreSucursal());
            sucursalExistente.setDireccionSucursal(sucursal.getDireccionSucursal());
            sucursalExistente.setApertura(sucursal.getApertura());
            sucursalExistente.setCierre(sucursal.getCierre());
            sucursalExistente.setPoliticaSucursal(sucursal.getPoliticaSucursal());
            
            sucursalService.save(sucursalExistente);
            return ResponseEntity.ok(sucursalExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal por su identificador")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            sucursalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
