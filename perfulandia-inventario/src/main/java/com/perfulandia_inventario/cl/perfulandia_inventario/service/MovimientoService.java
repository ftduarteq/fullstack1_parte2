package com.perfulandia_inventario.cl.perfulandia_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Movimiento;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.TipoMovimiento;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.InventarioRepository;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.MovimientoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Movimiento> findAll() {
        return movimientoRepository.findAll();
    }

    public Movimiento findById(Integer id) {
        return movimientoRepository.findById(id).get();
    }

    public Movimiento save(Movimiento movimiento) {
        Inventario inventario = inventarioRepository.findById(movimiento.getInventario().getId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        // Actualizar stock según el tipo de movimiento
        Integer cantidad = movimiento.getCantidad();
        if (movimiento.getTipoMovimiento() == TipoMovimiento.ENTRADA) {
            inventario.setStockProducto(inventario.getStockProducto() + cantidad);

        } else if (movimiento.getTipoMovimiento() == TipoMovimiento.SALIDA) {

            if (inventario.getStockProducto() < cantidad) {
                throw new RuntimeException("Stock insuficiente para realizar salida");
            }
            
            inventario.setStockProducto(inventario.getStockProducto() - cantidad);
        }

        inventarioRepository.save(inventario);

        movimiento.setInventario(inventario);
        movimiento.setStockResultante(inventario.getStockProducto());
        //movimiento.setFechaMovimiento(new Date()); // Registrar fecha actual

        return movimientoRepository.save(movimiento);
    }

    public void deleteById(Integer id) {
        Inventario inventario = inventarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));

        if (!movimientoRepository.findByInventario(inventario).isEmpty()) {
            throw new RuntimeException("No se puede eliminar el inventario porque tiene movimientos asociados");
        }

        inventarioRepository.deleteById(id);
    }


}