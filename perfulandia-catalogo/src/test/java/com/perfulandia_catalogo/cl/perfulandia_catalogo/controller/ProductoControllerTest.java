package com.perfulandia_catalogo.cl.perfulandia_catalogo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.CategoriaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.MarcaProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.OrigenProducto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.model.Producto;
import com.perfulandia_catalogo.cl.perfulandia_catalogo.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductoController.class)
@ActiveProfiles("test")
public class ProductoControllerTest {

    // Inyecta MockMvc para simular peticiones HTTP al controlador
    @Autowired
    private MockMvc mockMvc;

    // Inyecta ObjectMapper para convertir objetos Java a JSON y viceversa
    @Autowired
    private ObjectMapper objectMapper;

    // Mock del servicio Producto
    @MockitoBean
    private ProductoService productoService;

    @Test
    void testListarProductosConContenido() throws Exception {
        // Crea entidad relacionada para la prueba
        CategoriaProducto categoria = new CategoriaProducto(1, "Perfumería");
        OrigenProducto origen = new OrigenProducto(1, "Francia", "París");
        MarcaProducto marca = new MarcaProducto(1, "MarcaA");
        // Crea producto ejemplo con las relaciones
        Producto producto = new Producto(1, "Perfume A", "Fragancia floral", 19990.0, categoria, origen, marca);
        when(productoService.findAll()).thenReturn(List.of(producto));

        // GET /api/v1/productos y valida respuesta 200 y campo nombre
        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Perfume A"));
    }

    @Test
    void testBuscarProductoExistente() throws Exception {
        CategoriaProducto categoria = new CategoriaProducto(1, "Cosméticos");
        OrigenProducto origen = new OrigenProducto(1, "Italia", "Roma");
        MarcaProducto marca = new MarcaProducto(1, "MarcaB");
        Producto producto = new Producto(1, "Crema B", "Hidratante", 9990.0, categoria, origen, marca);
        when(productoService.findById(1)).thenReturn(producto);

        // GET /api/v1/productos/1 y valida que responde 200 con nombre correcto
        mockMvc.perform(get("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Crema B"));
    }

    @Test
    void testGuardarProducto() throws Exception {
        CategoriaProducto categoria = new CategoriaProducto(1, "Cuidado Personal");
        OrigenProducto origen = new OrigenProducto(1, "España", "Madrid");
        MarcaProducto marca = new MarcaProducto(1, "MarcaC");
        Producto nuevo = new Producto(null, "Jabón C", "Limpieza", 4990.0, categoria, origen, marca);
        Producto guardado = new Producto(2, "Jabón C", "Limpieza", 4990.0, categoria, origen, marca);
        when(productoService.save(any(Producto.class))).thenReturn(guardado);

        // POST producto nuevo y valida status 201 con id asignado
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    void testEliminarProductoExistente() throws Exception {
        // Simula que deleteById no lanza excepción
        doNothing().when(productoService).deleteById(1);

        // DELETE /api/v1/productos/1 y espera status 204 No Content
        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());
    }
}
