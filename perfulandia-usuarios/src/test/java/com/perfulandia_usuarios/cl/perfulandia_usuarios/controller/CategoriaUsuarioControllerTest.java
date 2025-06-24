package com.perfulandia_usuarios.cl.perfulandia_usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.service.CategoriaUsuarioService;
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

@WebMvcTest(CategoriaUsuarioController.class)
@ActiveProfiles("test")
public class CategoriaUsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaUsuarioService categoriaUsuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarCategorias() throws Exception {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        when(categoriaUsuarioService.findAll()).thenReturn(List.of(cat));

        mockMvc.perform(get("/api/v1/categoriasusuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoria").value("Gold"));
    }

    @Test
    public void testListarCategorias_NoContent() throws Exception {
        when(categoriaUsuarioService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/categoriasusuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGuardarCategoria() throws Exception {
        CategoriaUsuario input = new CategoriaUsuario(null, "Premium");
        CategoriaUsuario saved = new CategoriaUsuario(2, "Premium");

        when(categoriaUsuarioService.save(any(CategoriaUsuario.class))).thenReturn(saved);

        mockMvc.perform(post("/api/v1/categoriasusuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.categoria").value("Premium"));
    }
}
