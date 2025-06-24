package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.OrigenProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrigenProductoServiceTest {

    @MockitoBean
    private OrigenProductoRepository origenProductoRepository;

    @Autowired
    private OrigenProductoService origenProductoService;

    @Test
    void testFindAll() {
        List<OrigenProducto> origenes = List.of(new OrigenProducto(1, "Chile", "Región Metropolitana"));
        when(origenProductoRepository.findAll()).thenReturn(origenes);

        List<OrigenProducto> resultado = origenProductoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Chile", resultado.get(0).getPais());
    }

    @Test
    void testFindById() {
        OrigenProducto origen = new OrigenProducto(1, "Argentina", "Buenos Aires");
        when(origenProductoRepository.findById(1)).thenReturn(Optional.of(origen));

        OrigenProducto resultado = origenProductoService.findById(1);

        assertNotNull(resultado);
        assertEquals("Argentina", resultado.getPais());
    }

    @Test
    void testSave() {
        OrigenProducto nuevo = new OrigenProducto(null, "Perú", "Lima");
        OrigenProducto guardado = new OrigenProducto(2, "Perú", "Lima");
        when(origenProductoRepository.save(nuevo)).thenReturn(guardado);

        OrigenProducto resultado = origenProductoService.save(nuevo);

        assertNotNull(resultado);
        assertEquals(2, resultado.getId());
        assertEquals("Perú", resultado.getPais());
        assertEquals("Lima", resultado.getRegion());
    }

    @Test
    void testDeleteById() {
        doNothing().when(origenProductoRepository).deleteById(1);

        origenProductoService.deleteById(1);

        verify(origenProductoRepository, times(1)).deleteById(1);
    }
}
