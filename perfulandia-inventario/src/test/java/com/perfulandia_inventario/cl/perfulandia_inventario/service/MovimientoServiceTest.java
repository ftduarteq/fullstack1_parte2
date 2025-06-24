package com.perfulandia_inventario.cl.perfulandia_inventario.service;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.*;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.InventarioRepository;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class MovimientoServiceTest {

    @Autowired
    private MovimientoService movimientoService;

    @MockitoBean
    private MovimientoRepository movimientoRepository;

    @MockitoBean
    private InventarioRepository inventarioRepository;

    @Test
    public void testFindAll() {
        Movimiento m = new Movimiento();
        when(movimientoRepository.findAll()).thenReturn(Arrays.asList(m));
        List<Movimiento> result = movimientoService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        Movimiento m = new Movimiento();
        m.setId(1);
        when(movimientoRepository.findById(1)).thenReturn(Optional.of(m));
        Movimiento result = movimientoService.findById(1);
        assertEquals(1, result.getId());
    }

    @Test
    public void testGuardarEntrada() {
        Bodega bodega = new Bodega(1, "B1", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "P", 10, bodega);
        Movimiento entrada = new Movimiento(null, TipoMovimiento.ENTRADA, 5, null, new Date(), inventario);

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(i -> i.getArgument(0));

        Movimiento result = movimientoService.save(entrada);

        assertEquals(15, result.getStockResultante());
        assertEquals(15, inventario.getStockProducto());
    }

    @Test
    public void testGuardarSalidaConStock() {
        Bodega bodega = new Bodega(1, "B1", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "P", 20, bodega);
        Movimiento salida = new Movimiento(null, TipoMovimiento.SALIDA, 5, null, new Date(), inventario);

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(i -> i.getArgument(0));

        Movimiento result = movimientoService.save(salida);

        assertEquals(15, result.getStockResultante());
        assertEquals(15, inventario.getStockProducto());
    }

    @Test
    public void testGuardarSalidaSinStock() {
        Bodega bodega = new Bodega(1, "B1", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "P", 2, bodega);
        Movimiento salida = new Movimiento(null, TipoMovimiento.SALIDA, 5, null, new Date(), inventario);

        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            movimientoService.save(salida);
        });

        assertEquals("Stock insuficiente para realizar salida", thrown.getMessage());
    }

    @Test
    public void testDeleteByIdConMovimientos() {
        Bodega bodega = new Bodega(1, "B1", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "P", 20, bodega);
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));
        when(movimientoRepository.findByInventario(inventario)).thenReturn(List.of(new Movimiento()));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            movimientoService.deleteById(1);
        });

        assertEquals("No se puede eliminar el inventario porque tiene movimientos asociados", thrown.getMessage());
    }

    @Test
    public void testDeleteByIdSinMovimientos() {
        Bodega bodega = new Bodega(1, "B1", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "P", 20, bodega);
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));
        when(movimientoRepository.findByInventario(inventario)).thenReturn(Collections.emptyList());

        movimientoService.deleteById(1);
        verify(inventarioRepository).deleteById(1);
    }
}
