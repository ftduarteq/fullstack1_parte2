package com.perfulandia_catalogo.cl.perfulandia_catalogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.Producto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.CategoriaProductoRepository;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.MarcaProductoRepository;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.OrigenProductoRepository;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private OrigenProductoRepository origenProductoRepository;

    @Autowired
    private MarcaProductoRepository marcaProductoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(Integer id) {
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto) {

        CategoriaProducto categoria = categoriaProductoRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        OrigenProducto origen = origenProductoRepository.findById(producto.getOrigen().getId())
                .orElseThrow(() -> new RuntimeException("Origen no encontrado"));

        MarcaProducto marca = marcaProductoRepository.findById(producto.getMarca().getId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        producto.setCategoria(categoria);
        producto.setOrigen(origen);
        producto.setMarca(marca);

        return productoRepository.save(producto);
    }

    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
}
