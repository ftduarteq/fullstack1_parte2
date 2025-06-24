package com.perfulandia_usuarios.cl.perfulandia_usuarios.service;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.CategoriaUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoriaUsuarioServiceTest {

    @MockitoBean
    private CategoriaUsuarioRepository categoriaUsuarioRepository;

    @Autowired
    private CategoriaUsuarioService categoriaUsuarioService;

    @Test
    public void testFindAll() {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        when(categoriaUsuarioRepository.findAll()).thenReturn(List.of(cat));

        List<CategoriaUsuario> resultado = categoriaUsuarioService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Gold", resultado.get(0).getCategoria());
    }

    @Test
    public void testFindById() {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        when(categoriaUsuarioRepository.findById(1)).thenReturn(Optional.of(cat));

        CategoriaUsuario resultado = categoriaUsuarioService.findById(1);
        assertNotNull(resultado);
        assertEquals("Gold", resultado.getCategoria());
    }

    @Test
    public void testSave() {
        CategoriaUsuario cat = new CategoriaUsuario(null, "Premium");
        CategoriaUsuario saved = new CategoriaUsuario(2, "Premium");

        when(categoriaUsuarioRepository.save(cat)).thenReturn(saved);

        CategoriaUsuario resultado = categoriaUsuarioService.save(cat);
        assertNotNull(resultado);
        assertEquals(2, resultado.getId());
        assertEquals("Premium", resultado.getCategoria());
    }

    @Test
    public void testDelete() {
        doNothing().when(categoriaUsuarioRepository).deleteById(1);
        categoriaUsuarioService.delete(1);
        verify(categoriaUsuarioRepository, times(1)).deleteById(1);
    }
}
