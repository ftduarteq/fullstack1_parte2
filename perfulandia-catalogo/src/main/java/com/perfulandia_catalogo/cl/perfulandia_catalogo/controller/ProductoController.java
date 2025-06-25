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

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.Producto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista de todos los productos disponibles")
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> listaProductos = productoService.findAll();
        if (listaProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductos);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar producto por ID", description = "Busca un producto por su identificador")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar nuevo producto", description = "Guarda un nuevo producto en el sistema")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("{id}")
    @Operation(summary = "Actualizar producto existente", description = "Actualiza un producto existente con nuevos datos")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto productoExistente = productoService.findById(id);

            productoExistente.setNombre(producto.getNombre());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setPrecio(producto.getPrecio());
            
            productoExistente.setCategoria(producto.getCategoria());
            productoExistente.setOrigen(producto.getOrigen());
            productoExistente.setMarca(producto.getMarca());

            productoService.save(productoExistente);
            return ResponseEntity.ok(productoExistente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema por su identificador")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
