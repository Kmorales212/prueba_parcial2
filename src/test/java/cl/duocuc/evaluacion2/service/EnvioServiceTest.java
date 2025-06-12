package cl.duocuc.evaluacion2.service;



import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.repository.EnvioRepository;
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
public class EnvioServiceTest {

    @Autowired
    private EnvioService envioService;

    @MockBean
    private EnvioRepository envioRepository;

    @Test
    void testGetAllEnvios() {
        EnvioModelo envio1 = new EnvioModelo();
        EnvioModelo envio2 = new EnvioModelo();

        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        List<EnvioModelo> result = envioService.getAllEnvios();

        assertEquals(2, result.size());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testGetEnvioById_found() {
        EnvioModelo envio = new EnvioModelo();
        envio.setIdEnvio("123");

        when(envioRepository.findById("123")).thenReturn(Optional.of(envio));

        Optional<EnvioModelo> result = envioService.getEnvioById("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getIdEnvio());
    }

    @Test
    void testCreateEnvio() {
        EnvioModelo envio = new EnvioModelo();
        when(envioRepository.save(envio)).thenReturn(envio);

        EnvioModelo result = envioService.createEnvio(envio);

        assertNotNull(result);
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testUpdateEnvio_found() {
        EnvioModelo existing = new EnvioModelo();
        existing.setIdEnvio("123");

        EnvioModelo updated = new EnvioModelo();
        updated.setIdEnvio("123");

        when(envioRepository.findById("123")).thenReturn(Optional.of(existing));
        when(envioRepository.save(updated)).thenReturn(updated);

        Optional<EnvioModelo> result = envioService.updateEnvio("123", updated);

        assertTrue(result.isPresent());
        verify(envioRepository, times(1)).findById("123");
        verify(envioRepository, times(1)).save(updated);
    }

    @Test
    void testUpdateEnvio_notFound() {
        EnvioModelo updated = new EnvioModelo();
        when(envioRepository.findById("999")).thenReturn(Optional.empty());

        Optional<EnvioModelo> result = envioService.updateEnvio("999", updated);

        assertFalse(result.isPresent());
    }
}
