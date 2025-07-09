package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.model.*;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import cl.duocuc.evaluacion2.repository.EnvioRepository;
import cl.duocuc.evaluacion2.repository.RutaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RutaServiceTest {

    @Autowired
    private RutaService rutaService;

    @MockBean
    private RutaRepository rutaRepository;

    @MockBean
    private CiudadRepository ciudadRepository;

    @MockBean
    private EnvioRepository envioRepository;

    private CrearRutaDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CrearRutaDTO();
        dto.setIdRuta("RUTA-001");
        dto.setFechaInicio(LocalDate.of(2025, 6, 8).atStartOfDay());
        dto.setDescripcion("Ruta de prueba");
        dto.setIdDireccionInicio(1);
        dto.setIdDireccionDestino(2);
        dto.setCiudadId("1");
        dto.setIdsEnvios(List.of("ENV-001"));
    }


    @Test
    void testCrearRuta() {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        EnvioModelo envio = new EnvioModelo();
        envio.setIdEnvio("ENV-001");

        when(ciudadRepository.findById(1)).thenReturn(Optional.of(ciudad));
        when(envioRepository.findById("ENV-001")).thenReturn(Optional.of(envio));

        RutaModel rutaGuardada = new RutaModel();
        rutaGuardada.setIdRuta(dto.getIdRuta());

        when(rutaRepository.save(any())).thenReturn(rutaGuardada);

        RutaModel resultado = rutaService.crearRuta(dto);
        assertEquals("RUTA-001", resultado.getIdRuta());
    }

    @Test
    void testGetAllRutas() {
        RutaModel ruta = new RutaModel();
        ruta.setIdRuta("RUTA-001");

        when(rutaRepository.findAll()).thenReturn(List.of(ruta));

        List<RutaModel> resultado = rutaService.getAllRutas();
        assertEquals(1, resultado.size());
    }

    @Test
    void testEliminarRutaPorId() {
        rutaService.eliminarRutaPorId("RUTA-001");
        verify(rutaRepository, times(1)).deleteById("RUTA-001");
    }

    @Test
    void testActualizarEstado() {
        RutaModel ruta = new RutaModel();
        ruta.setIdRuta("RUTA-001");
        ruta.setEstado(EstadoRuta.PENDIENTE);

        when(rutaRepository.findById("RUTA-001")).thenReturn(Optional.of(ruta));
        when(rutaRepository.save(any())).thenReturn(ruta);

        RutaModel actualizado = rutaService.actualizarEstado("RUTA-001", EstadoRuta.EN_PROGRESO);
        assertEquals(EstadoRuta.EN_PROGRESO, actualizado.getEstado());
    }
}