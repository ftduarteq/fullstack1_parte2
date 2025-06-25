package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Personal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.PersonalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/personal")
@Tag(name = "Personal", description = "Operaciones relacionadas con personal")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping
    @Operation(summary = "Listar todo el personal", description = "Obtiene una lista de todo el personal registrado")
    public ResponseEntity<List<Personal>> listar() {
        List<Personal> lista = personalService.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar personal por ID", description = "Busca un personal por su identificador")
    public ResponseEntity<Personal> buscar(@PathVariable Integer id) {
        try {
            Personal personal = personalService.findById(id);
            return ResponseEntity.ok(personal);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo personal", description = "Registra un nuevo miembro del personal")
    public ResponseEntity<Personal> guardar(@RequestBody Personal personal) {
        try {
            Personal nuevoPersonal = personalService.save(personal);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPersonal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar personal existente", description = "Actualiza un registro de personal")
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
    @Operation(summary = "Eliminar personal", description = "Elimina un miembro del personal por su identificador")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            personalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
