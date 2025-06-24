package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.MarcaProductoRepository;
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
public class MarcaProductoServiceTest {

    @MockitoBean
    private MarcaProductoRepository marcaProductoRepository;

    @Autowired
    private MarcaProductoService marcaProductoService;

    @Test
    void testFindAll() {
        List<MarcaProducto> marcas = List.of(new MarcaProducto(1, "MarcaTest"));
        when(marcaProductoRepository.findAll()).thenReturn(marcas);

        List<MarcaProducto> resultado = marcaProductoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("MarcaTest", resultado.get(0).getNombre());
    }

    @Test
    void testFindById() {
        MarcaProducto marca = new MarcaProducto(1, "Marca1");
        when(marcaProductoRepository.findById(1)).thenReturn(Optional.of(marca));

        MarcaProducto resultado = marcaProductoService.findById(1);

        assertNotNull(resultado);
        assertEquals("Marca1", resultado.getNombre());
    }

    @Test
    void testSave() {
        MarcaProducto nueva = new MarcaProducto(null, "NuevaMarca");
        MarcaProducto guardada = new MarcaProducto(2, "NuevaMarca");
        when(marcaProductoRepository.save(nueva)).thenReturn(guardada);

        MarcaProducto resultado = marcaProductoService.save(nueva);

        assertNotNull(resultado);
        assertEquals(2, resultado.getId());
        assertEquals("NuevaMarca", resultado.getNombre());
    }

    @Test
    void testDeleteById() {
        doNothing().when(marcaProductoRepository).deleteById(1);

        marcaProductoService.deleteById(1);

        verify(marcaProductoRepository, times(1)).deleteById(1);
    }
}
