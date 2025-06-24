package com.perfulandia_catalogo.cl.perfulandia_catalogo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.CategoriaProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CategoriaProductoController.class)
@ActiveProfiles("test")
public class CategoriaProductoControllerTest {

    // Inyecta MockMvc para simular peticiones HTTP al controlador
    @Autowired
    private MockMvc mockMvc;

    // Inyecta ObjectMapper para convertir objetos Java a JSON y viceversa
    @Autowired
    private ObjectMapper objectMapper;

    // Crea un mock del servicio para controlar su comportamiento en los tests
    @MockitoBean
    private CategoriaProductoService categoriaProductoService;

    @Test
    void testListarCategoriasConContenido() throws Exception {
        // Crea una categoria de ejemplo para la prueba
        CategoriaProducto categoria = new CategoriaProducto(1, "Perfumería");
        // Simula que el servicio retorna una lista con la categoria creada
        when(categoriaProductoService.findAll()).thenReturn(List.of(categoria));

        // Ejecuta una petición GET y verifica que el resultado sea HTTP 200 y el JSON contenga la categoria
        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Perfumería"));
    }

    @Test
    void testBuscarCategoriaExistente() throws Exception {
        // Crea una categoria de ejemplo para la prueba
        CategoriaProducto categoria = new CategoriaProducto(1, "Maquillaje");
        // Simula que el servicio devuelve esa categoria cuando se busca por ID
        when(categoriaProductoService.findById(1)).thenReturn(categoria);

        // Ejecuta GET con ID=1 y verifica HTTP 200 con el nombre esperado
        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Maquillaje"));
    }

    @Test
    void testGuardarCategoria() throws Exception {
        // Crea un objeto sin ID (nuevo)
        CategoriaProducto nueva = new CategoriaProducto(null, "Cuidado Personal");
        // Simula la categoria guardada con ID asignado
        CategoriaProducto guardada = new CategoriaProducto(2, "Cuidado Personal");
        // Cuando el servicio guarde cualquier categoria, devuelve la guardada con ID
        when(categoriaProductoService.save(any(CategoriaProducto.class))).thenReturn(guardada);

        // Ejecuta POST con el JSON y espera HTTP 201 con el ID asignado
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void testEliminarCategoriaExistente() throws Exception {
        // Simula que el servicio no lanza excepcion al eliminar por ID
        doNothing().when(categoriaProductoService).deleteById(1);

        // Ejecuta DELETE y espera HTTP 204 No Content
        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());
    }
}
