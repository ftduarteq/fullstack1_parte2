package com.perfulandia_inventario.cl.perfulandia_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.service.BodegaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(BodegaController.class)
public class BodegaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BodegaService bodegaService;

    @Test
    public void testListar() throws Exception {
        Bodega bodega = new Bodega(1, "Bodega Norte", "Calle 123");
        when(bodegaService.findAll()).thenReturn(Arrays.asList(bodega));

        mockMvc.perform(get("/api/v1/bodegas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreBodega").value("Bodega Norte"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        Bodega bodega = new Bodega(1, "Bodega Sur", "Av. Principal");
        when(bodegaService.findById(1)).thenReturn(bodega);

        mockMvc.perform(get("/api/v1/bodegas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccionBodega").value("Av. Principal"));
    }

    @Test
    public void testGuardar() throws Exception {
        Bodega nueva = new Bodega(null, "Bodega Central", "Camino 45");
        Bodega guardada = new Bodega(1, "Bodega Central", "Camino 45");

        when(bodegaService.save(any(Bodega.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testActualizar() throws Exception {
        Bodega existente = new Bodega(1, "Bodega Vieja", "Antigua");
        Bodega actualizada = new Bodega(1, "Bodega Nueva", "Nueva Dir");

        when(bodegaService.findById(1)).thenReturn(existente);
        when(bodegaService.save(any(Bodega.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/bodegas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreBodega").value("Bodega Nueva"));
    }

    @Test
    public void testEliminar() throws Exception {
        doNothing().when(bodegaService).deleteById(1);

        mockMvc.perform(delete("/api/v1/bodegas/1"))
                .andExpect(status().isNoContent());
    }
}
