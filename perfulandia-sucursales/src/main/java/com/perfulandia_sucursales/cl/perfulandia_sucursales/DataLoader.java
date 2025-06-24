package com.perfulandia_sucursales.cl.perfulandia_sucursales;

import com.perfulandia_sucursales.cl.perfulandia_sucursales.model.*;
import com.perfulandia_sucursales.cl.perfulandia_sucursales.repository.*;

import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Profile("cargadev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private PoliticaSucursalRepository politicaSucursalRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private PersonalRepository personalRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        cargarCargos();
        cargarPoliticasSucursal();
        cargarSucursales();
        cargarPersonal();
    }

    private void cargarCargos() {
        String[][] cargos = {
            {"Gerente", "Administrativo"},
            {"Vendedor", "Operativo"},
            {"Cajero", "Operativo"},
            {"Supervisor", "Administrativo"}
        };

        for (String[] data : cargos) {
            Cargo cargo = new Cargo();
            cargo.setNombreCargo(data[0]);
            cargo.setTipoCargo(data[1]);
            cargoRepository.save(cargo);
        }
    }

    private void cargarPoliticasSucursal() {
        for (int i = 1; i <= 5; i++) {
            PoliticaSucursal politica = new PoliticaSucursal();
            politica.setNombrePolitica("Politica #" + i);
            politica.setDescripcion(faker.lorem().sentence());
            politicaSucursalRepository.save(politica);
        }
    }

    private void cargarSucursales() {
        List<PoliticaSucursal> politicas = politicaSucursalRepository.findAll();

        for (int i = 1; i <= 5; i++) {
            Sucursal sucursal = new Sucursal();
            sucursal.setNombreSucursal("Sucursal " + faker.address().cityName());
            sucursal.setDireccionSucursal(faker.address().fullAddress());
            sucursal.setApertura(LocalTime.of(9, 0));
            sucursal.setCierre(LocalTime.of(18, 0));
            sucursal.setPoliticaSucursal(politicas.get(random.nextInt(politicas.size())));
            sucursalRepository.save(sucursal);
        }
    }

    private void cargarPersonal() {
        List<Cargo> cargos = cargoRepository.findAll();
        List<Sucursal> sucursales = sucursalRepository.findAll();
        EstadoPersonal[] estados = EstadoPersonal.values();

        for (int i = 0; i < 20; i++) {
            Personal personal = new Personal();
            personal.setCodigoEmpleado("EMP" + faker.number().numberBetween(1000, 9999));
            personal.setEstadoPersonal(estados[random.nextInt(estados.length)]);
            personal.setCargo(cargos.get(random.nextInt(cargos.size())));
            personal.setSucursal(sucursales.get(random.nextInt(sucursales.size())));
            personalRepository.save(personal);
        }
    }
}
