package com.perfulandia_auth.cl.perfulandia_auth.service;

import com.perfulandia_auth.cl.perfulandia_auth.model.RolUsuario;
import com.perfulandia_auth.cl.perfulandia_auth.repository.RolUsuarioRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*; 

@SpringBootTest
@ActiveProfiles("test") // Usa configuracion del perfil test
public class RolUsuarioServiceTest {

    @Autowired
    private RolUsuarioService rolUsuarioService; // Servicio real con repositorios mockeados

    @MockitoBean
    private RolUsuarioRepository rolUsuarioRepository; // Simula el repositorio real

    @Test
    public void testFindAll() {
        // Simula que devuelve una lista con un rol de ejemplo
        when(rolUsuarioRepository.findAll()).thenReturn(List.of(new RolUsuario(1, "ADMIN")));

        // Ejecuta el metodo real
        List<RolUsuario> roles = rolUsuarioService.findAll();

        // Valida que haya un solo rol y que su nombre sea "ADMIN"
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getRol());
    }

    @Test
    public void testFindById() {
        // Crea un rol de prueba
        RolUsuario rol = new RolUsuario(1, "CLIENTE");
        // Simula que el repositorio lo encuentra por id
        when(rolUsuarioRepository.findById(1)).thenReturn(Optional.of(rol));

        // Llama al metodo real del servicio
        RolUsuario resultado = rolUsuarioService.findById(1);

        // Valida que el resultado no sea null y que el rol sea correcto
        assertNotNull(resultado);
        assertEquals("CLIENTE", resultado.getRol());
    }

    @Test
    public void testSave() {
        // Rol sin id (nuevo)
        RolUsuario rol = new RolUsuario(null, "VENDEDOR");
        // Simula que al guardar devuelve un rol con id
        when(rolUsuarioRepository.save(rol)).thenReturn(new RolUsuario(2, "VENDEDOR"));

        // Llama al metodo real para guardar
        RolUsuario resultado = rolUsuarioService.save(rol);

        // Valida que el resultado no sea null y que el nombre sea correcto
        assertNotNull(resultado);
        assertEquals("VENDEDOR", resultado.getRol());
    }

    @Test
    public void testDelete() {
        // Ejecuta el metodo delete (void)
        rolUsuarioService.delete(5);
        // Verifica que se llamo al metodo deleteById con id 5
        verify(rolUsuarioRepository).deleteById(5);
    }
}
