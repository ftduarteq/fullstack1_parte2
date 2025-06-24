package com.perfulandia_inventario.cl.perfulandia_inventario.service;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.BodegaRepository;
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
public class BodegaServiceTest {

    @Autowired
    private BodegaService bodegaService;

    @MockitoBean
    private BodegaRepository bodegaRepository;

    @Test
    public void testFindAll() {
        Bodega bodega = new Bodega(1, "Bodega 1", "Dirección 1");
        when(bodegaRepository.findAll()).thenReturn(Arrays.asList(bodega));

        List<Bodega> result = bodegaService.findAll();
        assertEquals(1, result.size());
        assertEquals("Bodega 1", result.get(0).getNombreBodega());
    }

    @Test
    public void testFindById() {
        Bodega bodega = new Bodega(1, "Bodega 2", "Dirección 2");
        when(bodegaRepository.findById(1)).thenReturn(Optional.of(bodega));

        Bodega result = bodegaService.findById(1);
        assertEquals("Bodega 2", result.getNombreBodega());
    }

    @Test
    public void testSave() {
        Bodega bodega = new Bodega(null, "Bodega Nueva", "Dirección Nueva");
        Bodega saved = new Bodega(1, "Bodega Nueva", "Dirección Nueva");

        when(bodegaRepository.save(bodega)).thenReturn(saved);

        Bodega result = bodegaService.save(bodega);
        assertEquals(1, result.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(bodegaRepository).deleteById(1);
        bodegaService.deleteById(1);
        verify(bodegaRepository, times(1)).deleteById(1);
    }
}
