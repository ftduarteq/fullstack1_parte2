package com.perfulandia_sucursales.cl.perfulandia_sucursales.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Cargo;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.service.CargoService;
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

@WebMvcTest(CargoController.class)
@ActiveProfiles("test")
public class CargoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CargoService cargoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarCargos() throws Exception {
        Cargo cargo = new Cargo(1, "Gerente", "Administrativo");
        when(cargoService.findAll()).thenReturn(List.of(cargo));

        mockMvc.perform(get("/api/v1/cargos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCargo").value("Gerente"));
    }

    @Test
    public void testBuscarCargoPorId() throws Exception {
        Cargo cargo = new Cargo(1, "Gerente", "Administrativo");
        when(cargoService.findById(1)).thenReturn(cargo);

        mockMvc.perform(get("/api/v1/cargos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoCargo").value("Administrativo"));
    }

    @Test
    public void testGuardarCargo() throws Exception {
        Cargo cargo = new Cargo(null, "Vendedor", "Operativo");
        Cargo guardado = new Cargo(1, "Vendedor", "Operativo");

        when(cargoService.save(any(Cargo.class))).thenReturn(guardado);

        mockMvc.perform(post("/api/v1/cargos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cargo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testEliminarCargo() throws Exception {
        doNothing().when(cargoService).deleteById(1);

        mockMvc.perform(delete("/api/v1/cargos/1"))
                .andExpect(status().isNoContent());
    }
}
