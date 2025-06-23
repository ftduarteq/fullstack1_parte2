package com.perfulandia_auth.cl.perfulandia_auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.service.RolUsuarioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // Para validar resultados HTTP
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // Para construir solicitudes HTTP

@WebMvcTest(RolUsuarioController.class) // Solo se carga el controlador RolUsuarioController
@ActiveProfiles("test") // Usa configuracion del perfil test
public class RolUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP

    @MockitoBean
    private RolUsuarioService rolUsuarioService; // Servicio simulado

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON y viceversa

    @Test
    public void testListarRoles() throws Exception {
        // Crea un rol para devolver en la lista
        RolUsuario rol = new RolUsuario(1, "ADMIN");
        // Simula que el servicio devuelve una lista con ese rol
        when(rolUsuarioService.findAll()).thenReturn(List.of(rol));

        // Realiza una llamada GET al endpoint y valida el resultado
        mockMvc.perform(get("/api/v1/rolesusuarios"))
                .andExpect(status().isOk()) // Código HTTP 200 OK esperado
                .andExpect(jsonPath("$[0].rol").value("ADMIN")); // Valida que el primer rol tenga nombre "ADMIN"
    }

    @Test
    public void testGuardarRol() throws Exception {
        // Rol de entrada sin id (nuevo)
        RolUsuario input = new RolUsuario(null, "VENDEDOR");
        // Simula que al guardar devuelve el rol con id 1
        RolUsuario resultado = new RolUsuario(1, "VENDEDOR");

        // Cuando el servicio guarda, devuelve el resultado simulado
        when(rolUsuarioService.save(any())).thenReturn(resultado);

        // Ejecuta la petición POST enviando el rol en JSON
        mockMvc.perform(post("/api/v1/rolesusuarios")
                .contentType(MediaType.APPLICATION_JSON) // Define tipo contenido JSON
                .content(objectMapper.writeValueAsString(input))) // Convierte input a JSON
                .andExpect(status().isCreated()) // Espera código HTTP 201 Created
                .andExpect(jsonPath("$.id").value(1)) // Valida que el id del resultado sea 1
                .andExpect(jsonPath("$.rol").value("VENDEDOR")); // Valida que el rol sea "VENDEDOR"
    }
}
