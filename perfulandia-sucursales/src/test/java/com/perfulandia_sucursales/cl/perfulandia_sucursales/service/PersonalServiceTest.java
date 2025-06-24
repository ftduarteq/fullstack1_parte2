package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.*;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.CargoRepository;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.PersonalRepository;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.SucursalRepository;
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
public class PersonalServiceTest {

    @MockitoBean
    private PersonalRepository personalRepository;

    @MockitoBean
    private CargoRepository cargoRepository;

    @MockitoBean
    private SucursalRepository sucursalRepository;

    @Autowired
    private PersonalService personalService;

    @Test
    public void testFindAll() {
        Personal p = new Personal(1, "EMP001", EstadoPersonal.ACTIVO, new Cargo(), new Sucursal());
        when(personalRepository.findAll()).thenReturn(List.of(p));

        List<Personal> personalList = personalService.findAll();
        assertFalse(personalList.isEmpty());
    }

    @Test
    public void testFindById() {
        Personal p = new Personal(1, "EMP001", EstadoPersonal.ACTIVO, new Cargo(), new Sucursal());
        when(personalRepository.findById(1)).thenReturn(Optional.of(p));

        Personal personal = personalService.findById(1);
        assertNotNull(personal);
        assertEquals("EMP001", personal.getCodigoEmpleado());
    }

    @Test
    public void testSave() {
        Cargo cargo = new Cargo(1, "Vendedor", "Operativo");
        Sucursal sucursal = new Sucursal(1, "Sucursal 1", "Dirección", null, null, null);

        Personal personal = new Personal(null, "EMP123", EstadoPersonal.ACTIVO, cargo, sucursal);

        when(cargoRepository.findById(1)).thenReturn(Optional.of(cargo));
        when(sucursalRepository.findById(1)).thenReturn(Optional.of(sucursal));
        when(personalRepository.save(any(Personal.class))).thenAnswer(i -> {
            Personal p = i.getArgument(0);
            p.setId(10);
            return p;
        });

        Personal saved = personalService.save(personal);
        assertEquals(10, saved.getId());
        assertEquals("EMP123", saved.getCodigoEmpleado());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(personalRepository).deleteById(1);
        assertDoesNotThrow(() -> personalService.deleteById(1));
        verify(personalRepository, times(1)).deleteById(1);
    }
}
