package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Personal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.PersonalService;

@RestController
@RequestMapping("api/v1/personal")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<Personal>> listar() {
        List<Personal> lista = personalService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    public ResponseEntity<Personal> buscar(@PathVariable Integer id) {
        try {
            Personal personal = personalService.findById(id);
            return ResponseEntity.ok(personal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Personal> guardar(@RequestBody Personal personal) {
        try {
            Personal nuevoPersonal = personalService.save(personal);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPersonal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Personal> actualizar(@PathVariable Integer id, @RequestBody Personal personal) {
        try {
            Personal personalExistente = personalService.findById(id);
            personalExistente.setCodigoEmpleado(personal.getCodigoEmpleado());
            personalExistente.setEstadoPersonal(personal.getEstadoPersonal());
            personalExistente.setCargo(personal.getCargo());
            personalExistente.setSucursal(personal.getSucursal());

            personalService.save(personalExistente);
            return ResponseEntity.ok(personalExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            personalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
