package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.PoliticaSucursalService;
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

@WebMvcTest(PoliticaSucursalController.class)
@ActiveProfiles("test")
public class PoliticaSucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PoliticaSucursalService politicaSucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarPoliticas() throws Exception {
        PoliticaSucursal politica = new PoliticaSucursal(1, "Horario", "Horario extendido");
        when(politicaSucursalService.findAll()).thenReturn(List.of(politica));

        mockMvc.perform(get("/api/v1/politicas-sucursal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombrePolitica").value("Horario"));
    }

    @Test
    public void testGuardarPolitica() throws Exception {
        PoliticaSucursal politica = new PoliticaSucursal(null, "Seguridad", "Normas de seguridad");
        PoliticaSucursal saved = new PoliticaSucursal(2, "Seguridad", "Normas de seguridad");

        when(politicaSucursalService.save(any(PoliticaSucursal.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/politicas-sucursal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(politica)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }
}
