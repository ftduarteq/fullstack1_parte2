package com.perfulandia_inventario.cl.perfulandia_inventario;

import com.perfulandia_inventario.cl.perfulandia_inventario.model.Bodega;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Inventario;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.Movimiento;
import com.perfulandia_inventario.cl.perfulandia_inventario.model.TipoMovimiento;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.BodegaRepository;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.InventarioRepository;
import com.perfulandia_inventario.cl.perfulandia_inventario.repository.MovimientoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Profile("cargadev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Crear bodegas
        for (int i = 0; i < 5; i++) {
            Bodega bodega = new Bodega();
            bodega.setNombreBodega("Bodega " + faker.address().cityName());
            bodega.setDireccionBodega(faker.address().fullAddress());
            bodegaRepository.save(bodega);
        }

        List<Bodega> bodegas = bodegaRepository.findAll();

        // Crear inventarios
        for (int i = 0; i < 20; i++) {
            Inventario inventario = new Inventario();
            inventario.setSkuProducto("SKU" + faker.number().digits(6));
            inventario.setNombreProducto(faker.commerce().productName());
            inventario.setStockProducto(random.nextInt(100) + 1);
            inventario.setBodega(bodegas.get(random.nextInt(bodegas.size())));
            inventarioRepository.save(inventario);
        }

        List<Inventario> inventarios = inventarioRepository.findAll();

        // Crear movimientos
        for (int i = 0; i < 50; i++) {
            Movimiento movimiento = new Movimiento();
            Inventario inventario = inventarios.get(random.nextInt(inventarios.size()));
            TipoMovimiento tipo = random.nextBoolean() ? TipoMovimiento.ENTRADA : TipoMovimiento.SALIDA;
            int cantidad = random.nextInt(20) + 1;
            int stockActual = inventario.getStockProducto();
            int nuevoStock = tipo == TipoMovimiento.ENTRADA ? stockActual + cantidad : Math.max(stockActual - cantidad, 0);
            inventario.setStockProducto(nuevoStock);
            inventarioRepository.save(inventario);

            movimiento.setTipoMovimiento(tipo);
            movimiento.setCantidad(cantidad);
            movimiento.setStockResultante(nuevoStock);
            movimiento.setFechaMovimiento(new Date());
            movimiento.setInventario(inventario);
            movimientoRepository.save(movimiento);
        }
    }
}
