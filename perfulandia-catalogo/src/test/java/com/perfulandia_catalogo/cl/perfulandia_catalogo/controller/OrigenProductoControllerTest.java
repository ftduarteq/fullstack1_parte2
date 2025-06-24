package com.perfulandia_catalogo.cl.perfulandia_catalogo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.OrigenProductoService;
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

@WebMvcTest(OrigenProductoController.class)
@ActiveProfiles("test")
public class OrigenProductoControllerTest {

    // Inyecta MockMvc para simular peticiones HTTP al controlador
    @Autowired
    private MockMvc mockMvc;

    // Inyecta ObjectMapper para convertir objetos Java a JSON y viceversa
    @Autowired
    private ObjectMapper objectMapper;

    // Mock del servicio OrigenProducto
    @MockitoBean
    private OrigenProductoService origenProductoService;

    @Test
    void testListarOrigenesConContenido() throws Exception {
        // Crea ejemplo con país y región
        OrigenProducto origen = new OrigenProducto(1, "Chile", "Región Metropolitana");
        when(origenProductoService.findAll()).thenReturn(List.of(origen));

        // GET y espera status 200 con origen correcto en JSON
        mockMvc.perform(get("/api/v1/origenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pais").value("Chile"))
                .andExpect(jsonPath("$[0].region").value("Región Metropolitana"));
    }

    @Test
    void testBuscarOrigenExistente() throws Exception {
        OrigenProducto origen = new OrigenProducto(1, "Argentina", "Buenos Aires");
        when(origenProductoService.findById(1)).thenReturn(origen);

        // GET por ID y espera 200 con datos esperados
        mockMvc.perform(get("/api/v1/origenes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pais").value("Argentina"))
                .andExpect(jsonPath("$.region").value("Buenos Aires"));
    }

    @Test
    void testGuardarOrigen() throws Exception {
        OrigenProducto nuevo = new OrigenProducto(null, "Perú", "Lima");
        OrigenProducto guardado = new OrigenProducto(2, "Perú", "Lima");
        when(origenProductoService.save(any(OrigenProducto.class))).thenReturn(guardado);

        // POST nuevo origen y espera status 201 con ID asignado
        mockMvc.perform(post("/api/v1/origenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void testEliminarOrigenExistente() throws Exception {
        // Simula delete sin error
        doNothing().when(origenProductoService).deleteById(1);

        // DELETE y espera 204 No Content
        mockMvc.perform(delete("/api/v1/origenes/1"))
                .andExpect(status().isNoContent());
    }
}
