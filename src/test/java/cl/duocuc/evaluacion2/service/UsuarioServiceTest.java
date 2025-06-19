package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioCompletoDTO;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import cl.duocuc.evaluacion2.repository.ComunaRepository;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import cl.duocuc.evaluacion2.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private DireccionRepository direccionRepository;

    @MockBean
    private ComunaRepository comunaRepository;

    @MockBean
    private CiudadRepository ciudadRepository;

    @Test
    void testRegistrarUsuarioCompleto_ciudadYComunaEncontradas() {
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("Pepe@correo.com");
        dto.setNombreCiudad("Santiago");
        dto.setNombreComuna("Ñuñoa");
        dto.setNombDireccion("Av. Central");
        dto.setNumDireccion(123);

        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");
        comuna.setCiudad(ciudad);

        UsuarioModelo usuarioMock = new UsuarioModelo();
        usuarioMock.setNombreUsur("Pepe");

        when(ciudadRepository.findAll()).thenReturn(Arrays.asList(ciudad));
        when(comunaRepository.findAll()).thenReturn(Arrays.asList(comuna));
        when(usuarioRepository.save(any())).thenReturn(usuarioMock);

        UsuarioModelo resultado = usuarioService.registrarUsuarioCompleto(dto);

        assertNotNull(resultado);
        assertEquals("Pepe", resultado.getNombreUsur());
        verify(usuarioRepository, times(1)).save(any());
        verify(direccionRepository, times(1)).save(any());
    }

    @Test
    public void testRegistrarUsuarioCompleto_ciudadNoEncontrada() {
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("pepe@correo.cl");
        dto.setNombreCiudad("CiudadInventada");
        dto.setNombreComuna("Ñuñoa");
        dto.setNombDireccion("Calle Falsa");
        dto.setNumDireccion(123);

        when(ciudadRepository.findAll()).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuarioCompleto(dto);
        });

        assertEquals("Ciudad no encontrada", exception.getMessage());
    }

    @Test
    void testRegistrarUsuarioCompleto_comunaNoEncontrada() {
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("Pepe@correo.com");
        dto.setNombreCiudad("Santiago");
        dto.setNombreComuna("Ñuñoa");

        dto.setNombDireccion("Calle Falsa");
        dto.setNumDireccion(123);

        // Ciudad correcta
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        // Comuna con nombre distinto y ciudad no relacionada
        CiudadModelo otraCiudad = new CiudadModelo();
        otraCiudad.setIdCiudad(2);
        otraCiudad.setNombCiudad("Valparaíso");

        ComunaModelo comunaNoCoincidente = new ComunaModelo();
        comunaNoCoincidente.setIdComuna(2);
        comunaNoCoincidente.setNomComuna("Providencia"); // <- diferente
        comunaNoCoincidente.setCiudad(otraCiudad); // <- ciudad también diferente

        when(ciudadRepository.findAll()).thenReturn(Arrays.asList(ciudad));
        when(comunaRepository.findAll()).thenReturn(Arrays.asList(comunaNoCoincidente));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuarioCompleto(dto);
        });

        assertEquals("Comuna no encontrada", exception.getMessage());
    }


    @Test
    public void testRegistrarUsuarioCompleto_direccionInvalida() {
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("Pepe@correo.cl");
        dto.setNombreCiudad("Santiago");
        dto.setNombreComuna("Ñuñoa");
        dto.setNombDireccion(null);
        dto.setNumDireccion(0);

        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");
        comuna.setCiudad(ciudad);

        when(ciudadRepository.findAll()).thenReturn(Arrays.asList(ciudad));
        when(comunaRepository.findAll()).thenReturn(Arrays.asList(comuna));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuarioCompleto(dto);
        });

        assertEquals("Datos de dirección inválidos", exception.getMessage());
    }
}