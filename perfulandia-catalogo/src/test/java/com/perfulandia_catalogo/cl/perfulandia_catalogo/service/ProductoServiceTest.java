package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.Producto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.CategoriaProductoRepository;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.MarcaProductoRepository;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.OrigenProductoRepository;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.ProductoRepository;
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
public class ProductoServiceTest {

    // Mock de ProductoRepository para simular base de datos
    @MockitoBean
    private ProductoRepository productoRepository;

    // Mock de repositorios relacionados para validaciones y asociaciones
    @MockitoBean
    private CategoriaProductoRepository categoriaProductoRepository;

    @MockitoBean
    private OrigenProductoRepository origenProductoRepository;

    @MockitoBean
    private MarcaProductoRepository marcaProductoRepository;

    // Servicio real inyectado para pruebas
    @Autowired
    private ProductoService productoService;

    // Prueba findAll con lista simulada
    @Test
    void testFindAll() {
        Producto producto = new Producto(1, "Perfume1", "Descripción", 5000.0,
                new CategoriaProducto(1, "Categoria1"),
                new OrigenProducto(1, "Chile", "RM"),
                new MarcaProducto(1, "Marca1"));
        List<Producto> productos = List.of(producto);
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.findAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Perfume1", resultado.get(0).getNombre());
    }

    // Prueba findById con objeto existente
    @Test
    void testFindById() {
        Producto producto = new Producto(1, "Perfume1", "Descripción", 5000.0,
                new CategoriaProducto(1, "Categoria1"),
                new OrigenProducto(1, "Chile", "RM"),
                new MarcaProducto(1, "Marca1"));
        when(productoRepository.findById(1)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.findById(1);

        assertNotNull(resultado);
        assertEquals("Perfume1", resultado.getNombre());
    }

    // Prueba para guardar un producto nuevo con entidades relacionadas existentes
    @Test
    void testSave() {
        CategoriaProducto categoria = new CategoriaProducto(1, "Categoria1");
        OrigenProducto origen = new OrigenProducto(1, "Chile", "RM");
        MarcaProducto marca = new MarcaProducto(1, "Marca1");

        Producto nuevoProducto = new Producto(null, "PerfumeNuevo", "Nueva descripción", 7500.0,
                categoria, origen, marca);

        Producto guardadoProducto = new Producto(2, "PerfumeNuevo", "Nueva descripción", 7500.0,
                categoria, origen, marca);

        // Simula la búsqueda de entidades relacionadas en repositorios
        when(categoriaProductoRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(origenProductoRepository.findById(1)).thenReturn(Optional.of(origen));
        when(marcaProductoRepository.findById(1)).thenReturn(Optional.of(marca));

        // Simula guardado en productoRepository
        when(productoRepository.save(any(Producto.class))).thenReturn(guardadoProducto);

        Producto resultado = productoService.save(nuevoProducto);

        assertNotNull(resultado);
        assertEquals(2, resultado.getId());
        assertEquals("PerfumeNuevo", resultado.getNombre());
    }

    // Prueba para eliminar producto por id
    @Test
    void testDeleteById() {
        doNothing().when(productoRepository).deleteById(1);

        productoService.deleteById(1);

        verify(productoRepository, times(1)).deleteById(1);
    }
}
