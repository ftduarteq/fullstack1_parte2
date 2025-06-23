package com.perfulandia_auth.cl.perfulandia_auth.service;

import com.perfulandia_auth.cl.perfulandia_auth.model.AuthUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.repository.AuthUsuarioRepository;
import com.perfulandia_auth.cl.perfulandia_auth.repository.RolUsuarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Usa configuracion del perfil test
public class AuthUsuarioServiceTest {

    @Autowired
    private AuthUsuarioService authUsuarioService; // Servicio real con repositorios mockeados

    @MockitoBean
    private AuthUsuarioRepository authUsuarioRepository; // Simula el repositorio real

    @MockitoBean
    private RolUsuarioRepository rolUsuarioRepository; // Simula el repositorio real

    @Test
    public void testFindAll() {
        // Simula que el repositorio devuelve una lista con un usuario dummy
        when(authUsuarioRepository.findAll()).thenReturn(List.of(new AuthUsuario()));

        // Llama el metodo real del servicio
        List<AuthUsuario> usuarios = authUsuarioService.findAll();

        // Valida que la lista tenga un elemento
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testFindById() {
        // Crea un usuario de prueba
        AuthUsuario user = new AuthUsuario(1, "admin", "1234", new RolUsuario(1, "ADMIN"));
        // Simula que findById devuelve ese usuario envuelto en Optional
        when(authUsuarioRepository.findById(1)).thenReturn(Optional.of(user));

        // Ejecuta el metodo real del servicio
        AuthUsuario resultado = authUsuarioService.findById(1);

        // Valida que no sea null y que username sea igual
        assertNotNull(resultado);
        assertEquals("admin", resultado.getUsername());
    }

    @Test
    public void testSave() {
        // Crea un rol dummy para asignar al usuario
        RolUsuario rol = new RolUsuario(1, "CLIENTE");
        // Crea un usuario a guardar sin id
        AuthUsuario user = new AuthUsuario(null, "nuevo", "pass", rol);

        // Simula que al buscar el rol por id, se encuentra el rol dummy
        when(rolUsuarioRepository.findById(1)).thenReturn(Optional.of(rol));
        // Simula que al guardar el usuario, devuelve el usuario con id asignado (simulación de DB)
        when(authUsuarioRepository.save(any())).thenReturn(new AuthUsuario(1, "nuevo", "pass", rol));

        // Llama al metodo real del servicio
        AuthUsuario resultado = authUsuarioService.save(user);

        // Valida que el resultado no sea null y que el username sea correcto
        assertNotNull(resultado);
        assertEquals("nuevo", resultado.getUsername());
    }

    @Test
    public void testDelete() {
        // Ejecuta el metodo delete (no devuelve nada)
        authUsuarioService.delete(10);
        // Verifica que el repositorio elimino el usuario con id 10
        verify(authUsuarioRepository).deleteById(10);
    }
}
