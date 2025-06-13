package cl.duocuc.evaluacion2.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import cl.duocuc.evaluacion2.controller.valeria.ProductoController;
import cl.duocuc.evaluacion2.dto.ProductoDTO;
import cl.duocuc.evaluacion2.model.*;
import cl.duocuc.evaluacion2.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoDTO productoDTO;
    private ProductoModelo productoModelo;
    @BeforeEach
    void setUp() {
        productoDTO = new ProductoDTO();
        productoDTO.setNombre("Producto A");
        productoDTO.setDescripcion("Descripción");
        productoDTO.setPrecio(99.9);

        productoModelo = new ProductoModelo();
        productoModelo.setId(1L);
        productoModelo.setNombre(productoDTO.getNombre());
        productoModelo.setDescripcion(productoDTO.getDescripcion());
        productoModelo.setPrecio(productoDTO.getPrecio());
    }
    @Test
    void testCrearProducto() throws Exception {
        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Producto A");
        dto.setDescripcion("Desc");
        dto.setPrecio(99.9);

        ProductoModelo producto = new ProductoModelo();
        producto.setId(1L);
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());

        when(productoService.crearProducto(any())).thenReturn(producto);

        mockMvc.perform(post("/api/productos/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testListarTodos() throws Exception {
        ProductoModelo p1 = new ProductoModelo();
        p1.setId(1L);
        p1.setNombre("P1");
        p1.setDescripcion("D1");
        p1.setPrecio(100.0);

        ProductoModelo p2 = new ProductoModelo();
        p2.setId(2L);
        p2.setNombre("P2");
        p2.setDescripcion("D2");
        p2.setPrecio(200.0);

        when(productoService.obtenerTodos()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/productos/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testBuscarPorId_found() throws Exception {
        ProductoModelo producto = new ProductoModelo();
        producto.setId(1L);
        producto.setNombre("P1");
        producto.setDescripcion("D1");
        producto.setPrecio(100.0);

        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testBuscarPorId_notFound() throws Exception {
        when(productoService.obtenerPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/productos/buscar/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarProducto() throws Exception {
        mockMvc.perform(delete("/api/productos/eliminar/1"))
                .andExpect(status().isOk());

        verify(productoService, times(1)).eliminarPorId(1L);
    }
}
