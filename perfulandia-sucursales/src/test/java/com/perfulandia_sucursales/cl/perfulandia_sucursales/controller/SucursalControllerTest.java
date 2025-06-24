package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Sucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.SucursalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(SucursalController.class)
@ActiveProfiles("test")
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarSucursales() throws Exception {
        Sucursal s = new Sucursal(1, "Sucursal Norte", "Calle 123", LocalTime.of(9, 0), LocalTime.of(18, 0), new PoliticaSucursal());
        when(sucursalService.findAll()).thenReturn(List.of(s));

        mockMvc.perform(get("/api/v1/sucursales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreSucursal").value("Sucursal Norte"));
    }

    @Test
    public void testGuardarSucursal() throws Exception {
        PoliticaSucursal politica = new PoliticaSucursal(1, "Normas", "Reglas internas");
        Sucursal s = new Sucursal(null, "Sucursal Sur", "Avenida X", LocalTime.of(8, 30), LocalTime.of(17, 30), politica);
        Sucursal saved = new Sucursal(2, s.getNombreSucursal(), s.getDireccionSucursal(), s.getApertura(), s.getCierre(), politica);

        when(sucursalService.save(any(Sucursal.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }
}
