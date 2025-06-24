package com.perfulandia_catalogo.cl.perfulandia_catalogo;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.*;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Profile("cargadev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private MarcaProductoRepository marcaProductoRepository;

    @Autowired
    private OrigenProductoRepository origenProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker fakerEs = new Faker(Locale.forLanguageTag("es"));
        Faker fakerUs = new Faker(Locale.US);
        Random random = new Random();

        // Crear categorias de productos
        for (int i = 0; i < 5; i++) {
            CategoriaProducto categoria = new CategoriaProducto();
            categoria.setNombre(fakerEs.commerce().department());
            categoriaProductoRepository.save(categoria);
        }

        // Crear marcas de productos
        for (int i = 0; i < 5; i++) {
            MarcaProducto marca = new MarcaProducto();
            marca.setNombre(fakerEs.company().name());
            marcaProductoRepository.save(marca);
        }

        // Crear origenes de productos
        for (int i = 0; i < 5; i++) {
            OrigenProducto origen = new OrigenProducto();
            origen.setPais(fakerEs.country().name());
            origen.setRegion(fakerEs.address().state());
            origenProductoRepository.save(origen);
        }

        List<CategoriaProducto> categorias = categoriaProductoRepository.findAll();
        List<MarcaProducto> marcas = marcaProductoRepository.findAll();
        List<OrigenProducto> origenes = origenProductoRepository.findAll();

        // Crear productos
        for (int i = 0; i < 20; i++) {
            Producto producto = new Producto();
            producto.setNombre(fakerEs.commerce().productName());
            producto.setDescripcion(fakerEs.lorem().sentence(8));
            producto.setPrecio(Double.parseDouble(fakerUs.commerce().price(5000.0, 50000.0)));
            producto.setCategoria(categorias.get(random.nextInt(categorias.size())));
            producto.setMarca(marcas.get(random.nextInt(marcas.size())));
            producto.setOrigen(origenes.get(random.nextInt(origenes.size())));
            productoRepository.save(producto);
        }
    }
}
