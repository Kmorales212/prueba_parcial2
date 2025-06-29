package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CiudadServiceTest {

    @Mock
    private CiudadRepository ciudadRepository;

    @InjectMocks
    private CiudadService ciudadService;

    public CiudadServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarCiudad() {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadRepository.save(any(CiudadModelo.class))).thenReturn(ciudad);

        CiudadModelo resultado = ciudadService.save(ciudad);

        assertEquals("Santiago", resultado.getNombCiudad());
        verify(ciudadRepository, times(1)).save(ciudad);
    }

    @Test
    void testBuscarCiudadPorId() {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudad));

        Optional<CiudadModelo> resultado = ciudadService.findById(1);

        assertEquals(true, resultado.isPresent());
        assertEquals("Santiago", resultado.get().getNombCiudad());
        verify(ciudadRepository, times(1)).findById(1);
    }
}
