package com.perfulandia_sucursales.cl.perfulandia_sucursales.service;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.Cargo;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.CargoRepository;
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
public class CargoServiceTest {

    @MockitoBean
    private CargoRepository cargoRepository;

    @Autowired
    private CargoService cargoService;

    @Test
    public void testFindAll() {
        Cargo c = new Cargo(1, "Gerente", "Administrativo");
        when(cargoRepository.findAll()).thenReturn(List.of(c));

        List<Cargo> cargos = cargoService.findAll();
        assertFalse(cargos.isEmpty());
        assertEquals("Gerente", cargos.get(0).getNombreCargo());
    }

    @Test
    public void testFindById() {
        Cargo c = new Cargo(1, "Gerente", "Administrativo");
        when(cargoRepository.findById(1)).thenReturn(Optional.of(c));

        Cargo cargo = cargoService.findById(1);
        assertNotNull(cargo);
        assertEquals("Administrativo", cargo.getTipoCargo());
    }

    @Test
    public void testSave() {
        Cargo c = new Cargo(null, "Vendedor", "Operativo");
        Cargo saved = new Cargo(2, "Vendedor", "Operativo");

        when(cargoRepository.save(c)).thenReturn(saved);

        Cargo result = cargoService.save(c);
        assertEquals(2, result.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(cargoRepository).deleteById(1);
        assertDoesNotThrow(() -> cargoService.deleteById(1));
        verify(cargoRepository, times(1)).deleteById(1);
    }
}
