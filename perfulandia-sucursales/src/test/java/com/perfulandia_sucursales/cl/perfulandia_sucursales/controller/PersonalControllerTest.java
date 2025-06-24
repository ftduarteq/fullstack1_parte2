package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.*;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.PersonalService;
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

@WebMvcTest(PersonalController.class)
@ActiveProfiles("test")
public class PersonalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonalService personalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarPersonal() throws Exception {
        Personal p = new Personal(1, "EMP001", EstadoPersonal.ACTIVO, new Cargo(), new Sucursal());
        when(personalService.findAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/v1/personal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoEmpleado").value("EMP001"));
    }

    @Test
    public void testBuscarPersonal() throws Exception {
        Personal p = new Personal(1, "EMP001", EstadoPersonal.ACTIVO, new Cargo(), new Sucursal());
        when(personalService.findById(1)).thenReturn(p);

        mockMvc.perform(get("/api/v1/personal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoPersonal").value("ACTIVO"));
    }

    @Test
    public void testGuardarPersonal() throws Exception {
        Personal p = new Personal(null, "EMP999", EstadoPersonal.ACTIVO, new Cargo(1, "Cajero", "Operativo"), new Sucursal(1, "Sucursal X", "Dirección", null, null, null));
        Personal saved = new Personal(99, "EMP999", EstadoPersonal.ACTIVO, p.getCargo(), p.getSucursal());

        when(personalService.save(any(Personal.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/personal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(99));
    }
}
