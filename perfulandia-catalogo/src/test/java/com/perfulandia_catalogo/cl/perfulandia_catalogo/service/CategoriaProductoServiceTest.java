package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.CategoriaProductoRepository;
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
public class CategoriaProductoServiceTest {

    // Mock del repositorio para simular comportamiento en pruebas
    @MockitoBean
    private CategoriaProductoRepository categoriaProductoRepository;

    // Inyección real del servicio a testear
    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @Test
    void testFindAll() {
        // Crea lista simulada de categorías
        List<CategoriaProducto> categorias = List.of(new CategoriaProducto(1, "CatTest"));
        // Configura comportamiento simulado del repositorio
        when(categoriaProductoRepository.findAll()).thenReturn(categorias);

        // Ejecuta el método del servicio
        List<CategoriaProducto> resultado = categoriaProductoService.findAll();

        // Verifica que resultado no sea nulo
        assertNotNull(resultado);
        // Verifica que contenga 1 elemento
        assertEquals(1, resultado.size());
        // Verifica que el nombre sea correcto
        assertEquals("CatTest", resultado.get(0).getNombre());
    }

    @Test
    void testFindById() {
        // Crea una categoría simulada
        CategoriaProducto categoria = new CategoriaProducto(1, "Categoria1");
        // Simula el comportamiento del repositorio para findById
        when(categoriaProductoRepository.findById(1)).thenReturn(Optional.of(categoria));

        // Ejecuta findById del servicio
        CategoriaProducto resultado = categoriaProductoService.findById(1);

        // Verifica que no sea nulo
        assertNotNull(resultado);
        // Verifica que el nombre sea el esperado
        assertEquals("Categoria1", resultado.getNombre());
    }

    @Test
    void testSave() {
        // Categoría nueva sin ID asignado
        CategoriaProducto nueva = new CategoriaProducto(null, "NuevaCategoria");
        // Categoría guardada simulada con ID
        CategoriaProducto guardada = new CategoriaProducto(2, "NuevaCategoria");
        // Simula el guardado en el repositorio
        when(categoriaProductoRepository.save(nueva)).thenReturn(guardada);

        // Ejecuta save en el servicio
        CategoriaProducto resultado = categoriaProductoService.save(nueva);

        // Verifica que el resultado no sea nulo
        assertNotNull(resultado);
        // Verifica que se haya asignado ID 2
        assertEquals(2, resultado.getId());
        // Verifica nombre correcto
        assertEquals("NuevaCategoria", resultado.getNombre());
    }

    @Test
    void testDeleteById() {
        // Simula que no hace nada al eliminar
        doNothing().when(categoriaProductoRepository).deleteById(1);

        // Ejecuta deleteById del servicio
        categoriaProductoService.deleteById(1);

        // Verifica que deleteById fue invocado exactamente una vez
        verify(categoriaProductoRepository, times(1)).deleteById(1);
    }
}
