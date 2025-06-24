package com.perfulandia_inventario.cl.perfulandia_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.service.InventarioService;
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
@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventarioService inventarioService;

    @Test
    public void testListar() throws Exception {
        Bodega bodega = new Bodega(1, "Bodega Central", "Dirección 1");
        Inventario inventario = new Inventario(1, "SKU001", "Perfume A", 50, bodega);
        when(inventarioService.findAll()).thenReturn(Arrays.asList(inventario));

        mockMvc.perform(get("/api/v1/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skuProducto").value("SKU001"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        Bodega bodega = new Bodega(1, "Bodega Central", "Dirección 1");
        Inventario inventario = new Inventario(1, "SKU002", "Perfume B", 30, bodega);
        when(inventarioService.findById(1)).thenReturn(inventario);

        mockMvc.perform(get("/api/v1/inventario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Perfume B"));
    }

    @Test
    public void testGuardar() throws Exception {
        Bodega bodega = new Bodega(1, "Bodega Central", "Dirección 1");
        Inventario nuevo = new Inventario(null, "SKU003", "Perfume C", 100, bodega);
        Inventario guardado = new Inventario(1, "SKU003", "Perfume C", 100, bodega);

        when(inventarioService.save(any(Inventario.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.skuProducto").value("SKU003"));
    }

    @Test
    public void testActualizar() throws Exception {
        Bodega bodega = new Bodega(1, "Bodega 1", "Dirección");
        Inventario existente = new Inventario(1, "SKU004", "Antiguo", 40, bodega);
        Inventario actualizado = new Inventario(1, "SKU004", "Nuevo", 60, bodega);

        when(inventarioService.findById(1)).thenReturn(existente);
        when(inventarioService.save(any(Inventario.class))).thenReturn(actualizado);

        mockMvc.perform(put("/api/v1/inventario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProducto").value("Nuevo"));
    }

    @Test
    public void testEliminar() throws Exception {
        doNothing().when(inventarioService).deleteById(1);

        mockMvc.perform(delete("/api/v1/inventario/1"))
                .andExpect(status().isNoContent());
    }

}
