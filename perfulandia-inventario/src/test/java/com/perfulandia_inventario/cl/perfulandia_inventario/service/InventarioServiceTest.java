package com.perfulandia_inventario.cl.perfulandia_inventario.service;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.BodegaRepository;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class InventarioServiceTest {

    @Autowired
    private InventarioService inventarioService;

    @MockitoBean
    private InventarioRepository inventarioRepository;

    @MockitoBean
    private BodegaRepository bodegaRepository;

    @Test
    public void testFindAll() {
        Bodega bodega = new Bodega(1, "Bodega", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "Producto", 10, bodega);
        when(inventarioRepository.findAll()).thenReturn(Arrays.asList(inventario));

        List<Inventario> result = inventarioService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testFindById() {
        Bodega bodega = new Bodega(1, "Bodega", "Dir");
        Inventario inventario = new Inventario(1, "SKU", "Producto", 10, bodega);
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventario));

        Inventario result = inventarioService.findById(1);
        assertEquals("Producto", result.getNombreProducto());
    }

    @Test
    public void testSave() {
        Bodega bodega = new Bodega(1, "Bodega", "Dir");
        Inventario inventario = new Inventario(null, "SKU", "Nuevo", 5, bodega);

        when(bodegaRepository.findById(1)).thenReturn(Optional.of(bodega));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(new Inventario(1, "SKU", "Nuevo", 5, bodega));

        Inventario result = inventarioService.save(inventario);
        assertNotNull(result);
        assertEquals("Nuevo", result.getNombreProducto());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(inventarioRepository).deleteById(1);
        inventarioService.deleteById(1);
        verify(inventarioRepository, times(1)).deleteById(1);
    }
}
