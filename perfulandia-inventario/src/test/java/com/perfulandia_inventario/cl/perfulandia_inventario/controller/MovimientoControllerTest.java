package com.perfulandia_inventario.cl.perfulandia_inventario.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.*;
import com.perfulandia_inventario.cl.perfulandia_inventario.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(MovimientoController.class)
public class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovimientoService movimientoService;

    @Test
    public void testListar() throws Exception {
        Inventario inventario = new Inventario(1, "SKU001", "Producto", 100, new Bodega(1, "Bodega", "Dir"));
        Movimiento movimiento = new Movimiento(1, TipoMovimiento.ENTRADA, 20, 120, new Date(), inventario);
        when(movimientoService.findAll()).thenReturn(Arrays.asList(movimiento));

        mockMvc.perform(get("/api/v1/movimientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoMovimiento").value("ENTRADA"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        Inventario inventario = new Inventario(1, "SKU002", "Producto", 80, new Bodega(1, "Bodega", "Dir"));
        Movimiento movimiento = new Movimiento(1, TipoMovimiento.SALIDA, 10, 70, new Date(), inventario);
        when(movimientoService.findById(1)).thenReturn(movimiento);

        mockMvc.perform(get("/api/v1/movimientos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoMovimiento").value("SALIDA"));
    }

    @Test
    public void testGuardar() throws Exception {
        Inventario inventario = new Inventario(1, "SKU003", "Producto", 50, new Bodega(1, "Bodega", "Dir"));
        Movimiento nuevo = new Movimiento(null, TipoMovimiento.ENTRADA, 25, 75, new Date(), inventario);
        Movimiento guardado = new Movimiento(1, TipoMovimiento.ENTRADA, 25, 75, new Date(), inventario);

        when(movimientoService.save(any(Movimiento.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGuardarConError() throws Exception {
        Inventario inventario = new Inventario(1, "SKU003", "Producto", 50, new Bodega(1, "Bodega", "Dir"));
        Movimiento nuevo = new Movimiento(null, TipoMovimiento.SALIDA, 60, 0, new Date(), inventario);

        when(movimientoService.save(any(Movimiento.class)))
                .thenThrow(new RuntimeException("Stock insuficiente para realizar salida"));

        mockMvc.perform(post("/api/v1/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Stock insuficiente para realizar salida"));
    }

    @Test
    public void testEliminar() throws Exception {
        doNothing().when(movimientoService).deleteById(1);

        mockMvc.perform(delete("/api/v1/movimientos/1"))
                .andExpect(status().isNoContent());
    }

}
