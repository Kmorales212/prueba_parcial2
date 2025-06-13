package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.ProductoModelo;
import cl.duocuc.evaluacion2.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    void testCrearProducto() {
        ProductoModelo producto = new ProductoModelo();
        when(productoRepository.save(producto)).thenReturn(producto);

        ProductoModelo result = productoService.crearProducto(producto);

        assertNotNull(result);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testObtenerTodos() {
        ProductoModelo p1 = new ProductoModelo();
        ProductoModelo p2 = new ProductoModelo();

        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<ProductoModelo> result = productoService.obtenerTodos();

        assertEquals(2, result.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_encontrado() {
        ProductoModelo producto = new ProductoModelo();
        producto.setId(1L);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<ProductoModelo> result = productoService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testObtenerPorId_noEncontrado() {
        when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ProductoModelo> result = productoService.obtenerPorId(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testEliminarPorId() {
        Long id = 1L;
        productoService.eliminarPorId(id);

        verify(productoRepository, times(1)).deleteById(id);
    }
}
