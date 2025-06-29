package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DireccionServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionService direccionService;

    public DireccionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarDireccion() {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");
        direccion.setNumDireccion(123);

        when(direccionRepository.save(any(DireccionModelo.class))).thenReturn(direccion);

        DireccionModelo resultado = direccionService.save(direccion);

        assertEquals("Calle Falsa", resultado.getNombDireccion());
        verify(direccionRepository, times(1)).save(direccion);
    }

    @Test
    void testBuscarDireccionPorId() {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");

        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));

        Optional<DireccionModelo> resultado = direccionService.findById(1);

        assertEquals(true, resultado.isPresent());
        assertEquals("Calle Falsa", resultado.get().getNombDireccion());
        verify(direccionRepository, times(1)).findById(1);
    }
}
