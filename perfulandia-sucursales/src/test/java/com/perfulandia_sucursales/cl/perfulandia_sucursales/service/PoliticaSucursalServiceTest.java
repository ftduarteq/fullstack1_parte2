package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.PoliticaSucursalRepository;
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
public class PoliticaSucursalServiceTest {

    @MockitoBean
    private PoliticaSucursalRepository politicaSucursalRepository;

    @Autowired
    private PoliticaSucursalService politicaSucursalService;

    @Test
    public void testFindAll() {
        PoliticaSucursal p = new PoliticaSucursal(1, "Normas", "Descripción");
        when(politicaSucursalRepository.findAll()).thenReturn(List.of(p));

        List<PoliticaSucursal> politicas = politicaSucursalService.findAll();
        assertFalse(politicas.isEmpty());
    }

    @Test
    public void testFindById() {
        PoliticaSucursal p = new PoliticaSucursal(1, "Normas", "Descripción");
        when(politicaSucursalRepository.findById(1)).thenReturn(Optional.of(p));

        PoliticaSucursal politica = politicaSucursalService.findById(1);
        assertNotNull(politica);
        assertEquals("Normas", politica.getNombrePolitica());
    }

    @Test
    public void testSave() {
        PoliticaSucursal p = new PoliticaSucursal(null, "Seguridad", "Normas de seguridad");
        PoliticaSucursal saved = new PoliticaSucursal(2, "Seguridad", "Normas de seguridad");

        when(politicaSucursalRepository.save(p)).thenReturn(saved);

        PoliticaSucursal result = politicaSucursalService.save(p);
        assertEquals(2, result.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(politicaSucursalRepository).deleteById(1);
        assertDoesNotThrow(() -> politicaSucursalService.deleteById(1));
        verify(politicaSucursalRepository, times(1)).deleteById(1);
    }
}
