package com.perfulandia_catalogo.cl.perfulandia_catalogo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.MarcaProductoService;
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

@WebMvcTest(MarcaProductoController.class)
@ActiveProfiles("test")
public class MarcaProductoControllerTest {

    // Inyecta MockMvc para simular peticiones HTTP al controlador
    @Autowired
    private MockMvc mockMvc;

    // Inyecta ObjectMapper para convertir objetos Java a JSON y viceversa
    @Autowired
    private ObjectMapper objectMapper;

    // Crea un mock del servicio MarcaProducto
    @MockitoBean
    private MarcaProductoService marcaProductoService;

    @Test
    void testListarMarcasConContenido() throws Exception {
        // Crea una marca de ejemplo
        MarcaProducto marca = new MarcaProducto(1, "MarcaX");
        // Simula respuesta del servicio con la marca creada
        when(marcaProductoService.findAll()).thenReturn(List.of(marca));

        // Realiza GET y verifica que retorna status 200 y la marca en JSON
        mockMvc.perform(get("/api/v1/marcas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("MarcaX"));
    }

    @Test
    void testBuscarMarcaExistente() throws Exception {
        MarcaProducto marca = new MarcaProducto(1, "MarcaY");
        when(marcaProductoService.findById(1)).thenReturn(marca);

        // Realiza GET por ID y espera status 200 con marca correcta
        mockMvc.perform(get("/api/v1/marcas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("MarcaY"));
    }

    @Test
    void testGuardarMarca() throws Exception {
        MarcaProducto nueva = new MarcaProducto(null, "MarcaNueva");
        MarcaProducto guardada = new MarcaProducto(2, "MarcaNueva");
        when(marcaProductoService.save(any(MarcaProducto.class))).thenReturn(guardada);

        // POST con marca nueva y espera status 201 y id asignado
        mockMvc.perform(post("/api/v1/marcas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void testEliminarMarcaExistente() throws Exception {
        // Simula metodo delete sin excepcion
        doNothing().when(marcaProductoService).deleteById(1);

        // Ejecuta DELETE y espera status 204 No Content
        mockMvc.perform(delete("/api/v1/marcas/1"))
                .andExpect(status().isNoContent());
    }
}
