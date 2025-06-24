package com.perfulandia_usuarios.cl.perfulandia_usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.Usuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UsuarioController.class)
@ActiveProfiles("test")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarUsuarios() throws Exception {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario user = new Usuario(1, "user1", "Juan", "Perez", "12345678-K", new Date(), cat);
        when(usuarioService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"));
    }

    @Test
    public void testGuardarUsuario() throws Exception {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario input = new Usuario(null, "user123", "Carlos", "Lopez", "12345678-K", new Date(), cat);
        Usuario saved = new Usuario(1, "user123", "Carlos", "Lopez", "12345678-K", new Date(), cat);

        when(usuarioService.save(any(Usuario.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user123"));
    }

    @Test
    public void testBuscarUsuario() throws Exception {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario user = new Usuario(1, "user1", "Juan", "Perez", "12345678-K", new Date(), cat);

        when(usuarioService.findById(1)).thenReturn(user);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void testActualizarUsuario() throws Exception {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario existing = new Usuario(1, "user1", "Juan", "Perez", "12345678-K", new Date(), cat);
        Usuario update = new Usuario(null, "userUpdated", "Juanito", "Perezito", "98765432-K", new Date(), cat);

        when(usuarioService.findById(1)).thenReturn(existing);
        when(usuarioService.save(any(Usuario.class))).thenReturn(existing);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("userUpdated"));
    }

    @Test
    public void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
