package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.PoliticaSucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Sucursal;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.PoliticaSucursalRepository;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class SucursalServiceTest {

    @MockitoBean
    private SucursalRepository sucursalRepository;

    @MockitoBean
    private PoliticaSucursalRepository politicaSucursalRepository;

    @Autowired
    private SucursalService sucursalService;

    @Test
    public void testFindAll() {
        Sucursal s = new Sucursal(1, "Sucursal A", "Dirección", LocalTime.of(9, 0), LocalTime.of(18, 0), new PoliticaSucursal());
        when(sucursalRepository.findAll()).thenReturn(List.of(s));

        List<Sucursal> sucursales = sucursalService.findAll();
        assertFalse(sucursales.isEmpty());
    }

    @Test
    public void testFindById() {
        Sucursal s = new Sucursal(1, "Sucursal A", "Dirección", LocalTime.of(9, 0), LocalTime.of(18, 0), new PoliticaSucursal());
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(s));

        Sucursal sucursal = sucursalService.findById(1);
        assertNotNull(sucursal);
        assertEquals("Sucursal A", sucursal.getNombreSucursal());
    }

    @Test
    public void testSave() {
        PoliticaSucursal politica = new PoliticaSucursal(1, "Normas", "Reglas");
        Sucursal s = new Sucursal(null, "Sucursal B", "Dir", LocalTime.of(8, 0), LocalTime.of(17, 0), politica);
        Sucursal saved = new Sucursal(2, "Sucursal B", "Dir", LocalTime.of(8, 0), LocalTime.of(17, 0), politica);

        when(politicaSucursalRepository.findById(1)).thenReturn(Optional.of(politica));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(saved);

        Sucursal result = sucursalService.save(s);
        assertEquals(2, result.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(sucursalRepository).deleteById(1);
        assertDoesNotThrow(() -> sucursalService.deleteById(1));
        verify(sucursalRepository, times(1)).deleteById(1);
    }
}
