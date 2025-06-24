package com.perfulandia_usuarios.cl.perfulandia_usuarios.service;

import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.CategoriaUsuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.model.Usuario;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.CategoriaUsuarioRepository;
import com.perfulandia_usuarios.cl.perfulandia_usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @MockitoBean
    private CategoriaUsuarioRepository categoriaUsuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void testFindAll() {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario user = new Usuario(1, "user1", "Juan", "Perez", "12345678-K", new Date(), cat);

        when(usuarioRepository.findAll()).thenReturn(List.of(user));

        List<Usuario> resultado = usuarioService.findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("user1", resultado.get(0).getUsername());
    }

    @Test
    public void testFindById() {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario user = new Usuario(1, "user1", "Juan", "Perez", "12345678-K", new Date(), cat);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));

        Usuario resultado = usuarioService.findById(1);
        assertNotNull(resultado);
        assertEquals("user1", resultado.getUsername());
    }

    @Test
    public void testSave() {
        CategoriaUsuario cat = new CategoriaUsuario(1, "Gold");
        Usuario input = new Usuario(null, "user123", "Carlos", "Lopez", "12345678-K", new Date(), cat);
        Usuario saved = new Usuario(1, "user123", "Carlos", "Lopez", "12345678-K", new Date(), cat);

        when(categoriaUsuarioRepository.findById(cat.getId())).thenReturn(Optional.of(cat));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(saved);

        Usuario resultado = usuarioService.save(input);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("user123", resultado.getUsername());
        verify(categoriaUsuarioRepository, times(1)).findById(cat.getId());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testSave_CategoriaNoEncontrada() {
        CategoriaUsuario cat = new CategoriaUsuario(99, "NoExiste");
        Usuario input = new Usuario(null, "userX", "Test", "Test", "11111111-1", new Date(), cat);

        when(categoriaUsuarioRepository.findById(cat.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.save(input);
        });

        assertEquals("Categoria no encontrada", exception.getMessage());
    }

    @Test
    public void testDelete() {
        doNothing().when(usuarioRepository).deleteById(1);
        usuarioService.delete(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }
}
