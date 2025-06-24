package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

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

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Cargo;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.CargoService;

@RestController
@RequestMapping("api/v1/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<Cargo>> listar() {
        List<Cargo> listaCargos = cargoService.findAll();
        if (listaCargos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaCargos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Cargo> buscar(@PathVariable Integer id) {
        try {
            Cargo cargo = cargoService.findById(id);
            return ResponseEntity.ok(cargo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cargo> guardar(@RequestBody Cargo cargo) {
        Cargo nuevoCargo = cargoService.save(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCargo);
    }

    @PutMapping("{id}")
    public ResponseEntity<Cargo> actualizar(@PathVariable Integer id, @RequestBody Cargo cargo) {
        try {
            Cargo cargoExistente = cargoService.findById(id);

            cargoExistente.setNombreCargo(cargo.getNombreCargo());
            cargoExistente.setTipoCargo(cargo.getTipoCargo());

            cargoService.save(cargoExistente);
            return ResponseEntity.ok(cargoExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Integer id) {
        try {
            cargoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
