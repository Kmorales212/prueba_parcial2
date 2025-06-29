package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.repository.ComunaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private ComunaService comunaService;

    public ComunaServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarComuna() {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaRepository.save(any(ComunaModelo.class))).thenReturn(comuna);

        ComunaModelo resultado = comunaService.save(comuna);

        assertEquals("Ñuñoa", resultado.getNomComuna());
        verify(comunaRepository, times(1)).save(comuna);
    }

    @Test
    void testBuscarComunaPorId() {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));

        Optional<ComunaModelo> resultado = comunaService.findById(1);

        assertEquals(true, resultado.isPresent());
        assertEquals("Ñuñoa", resultado.get().getNomComuna());
        verify(comunaRepository, times(1)).findById(1);
    }
}
