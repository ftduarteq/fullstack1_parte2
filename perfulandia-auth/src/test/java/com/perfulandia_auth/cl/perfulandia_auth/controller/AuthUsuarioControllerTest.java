package com.perfulandia_auth.cl.perfulandia_auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper; // Para convertir objetos Java en JSON
import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.service.AuthUsuarioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // Configura solo el controlador
import org.springframework.http.MediaType; // Para definir el tipo de contenido (application/json)
import org.springframework.test.context.ActiveProfiles; // Usa el perfil de configuración "test"
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Crea mocks
import org.springframework.test.web.servlet.MockMvc; // Simula peticiones HTTP

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*; // Para configurar mocks y verificar llamadas
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // Para validar resultados HTTP
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // Para construir solicitudes HTTP

@WebMvcTest(AuthUsuarioController.class) // Solo carga el controlador AuthUsuarioController
@ActiveProfiles("test") // Usa configuracion del perfil test
public class AuthUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula peticiones HTTP

    @MockitoBean
    private AuthUsuarioService authUsuarioService; // Servicio simulado

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON y viceversa

    @Test
    public void testListarUsuarios() throws Exception {
        // Prepara un usuario simulado para que el servicio lo devuelva
        AuthUsuario user = new AuthUsuario(1, "admin", "1234", new RolUsuario(1, "ADMIN"));
        when(authUsuarioService.findAll()).thenReturn(List.of(user)); // Simula respuesta

        // Realiza la llamada GET y valida la respuesta JSON
        mockMvc.perform(get("/api/v1/authusuarios"))
                .andExpect(status().isOk()) // Espera codigo 200 OK
                .andExpect(jsonPath("$[0].username").value("admin")); // Valida que el primer username sea "admin"
    }

    @Test
    public void testGuardarUsuario() throws Exception {
        // Usuario de entrada (no tiene ID aun)
        AuthUsuario input = new AuthUsuario(null, "nuevo", "clave", new RolUsuario(1, "CLIENTE"));

        // Usuario que simula ser el guardado por el servicio (ya con ID)
        AuthUsuario resultado = new AuthUsuario(1, "nuevo", "clave", input.getRolUsuario());

        // Configura el mock para que al guardar devuelva el usuario con ID
        when(authUsuarioService.save(any())).thenReturn(resultado);

        // Ejecuta POST enviando el usuario como JSON
        mockMvc.perform(post("/api/v1/authusuarios")
                .contentType(MediaType.APPLICATION_JSON) // Tipo de contenido enviado
                .content(objectMapper.writeValueAsString(input))) // JSON del objeto input
                .andExpect(status().isCreated()) // Espera codigo 201 Created
                .andExpect(jsonPath("$.id").value(1)) // Valida que el ID en la respuesta sea 1
                .andExpect(jsonPath("$.username").value("nuevo")); // Valida que el username sea "nuevo"
    }

    @Test
    public void testBuscarPorId() throws Exception {
        // Simula un usuario existente
        AuthUsuario user = new AuthUsuario(1, "juan", "clave", new RolUsuario(1, "ADMIN"));

        // Simula que el servicio lo encuentra por ID
        when(authUsuarioService.findById(1)).thenReturn(user);

        // Llama al endpoint GET /authusuarios/1 y valida la respuesta
        mockMvc.perform(get("/api/v1/authusuarios/1"))
                .andExpect(status().isOk()) // Espera 200 OK
                .andExpect(jsonPath("$.username").value("juan")); // Valida que el username devuelto sea "juan"
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        // Simula que el servicio elimina sin errores
        doNothing().when(authUsuarioService).delete(1);

        // Llama al endpoint DELETE /authusuarios/1 y espera NoContent
        mockMvc.perform(delete("/api/v1/authusuarios/1"))
                .andExpect(status().isNoContent()); // Espera código 204 No Content
    }
}
