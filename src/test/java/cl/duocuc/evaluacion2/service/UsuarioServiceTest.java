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
import java.util.List;

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
    void testRegistrarUsuarioCompleto_ciudadSeCreaYComunaExiste() {
        // Arrange
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("pepe@correo.cl");
        dto.setNombreCiudad("CiudadInventada");
        dto.setNombreComuna("Ñuñoa");
        dto.setNombDireccion("Calle Falsa");
        dto.setNumDireccion(123);

        // No hay ciudades
        when(ciudadRepository.findAll()).thenReturn(List.of());

        // La comuna sí existe
        CiudadModelo otraCiudad = new CiudadModelo();
        otraCiudad.setIdCiudad(1);
        otraCiudad.setNombCiudad("OtraCiudad");

        ComunaModelo comunaExistente = new ComunaModelo();
        comunaExistente.setIdComuna(1);
        comunaExistente.setNomComuna("Ñuñoa");
        comunaExistente.setCiudad(otraCiudad);

        when(comunaRepository.findAll()).thenReturn(List.of(comunaExistente));

        // Simular creación de ciudad
        CiudadModelo ciudadCreada = new CiudadModelo();
        ciudadCreada.setIdCiudad(99);
        ciudadCreada.setNombCiudad("CiudadInventada");

        when(ciudadRepository.save(any(CiudadModelo.class))).thenReturn(ciudadCreada);

        when(direccionRepository.save(any(DireccionModelo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(usuarioRepository.save(any(UsuarioModelo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioModelo resultado = usuarioService.registrarUsuarioCompleto(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pepe", resultado.getNombreUsur());

        verify(ciudadRepository, times(1)).save(any(CiudadModelo.class));
        verify(comunaRepository, never()).save(any(ComunaModelo.class));
        verify(direccionRepository, times(1)).save(any(DireccionModelo.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioModelo.class));
    }


    @Test
    void testRegistrarUsuarioCompleto_ciudadExisteYComunaSeCrea() {
        // Arrange
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("pepe@correo.cl");
        dto.setNombreCiudad("Santiago");
        dto.setNombreComuna("Ñuñoa");
        dto.setNombDireccion("Calle Falsa");
        dto.setNumDireccion(123);

        // Ciudad existente
        CiudadModelo ciudadExistente = new CiudadModelo();
        ciudadExistente.setIdCiudad(1);
        ciudadExistente.setNombCiudad("Santiago");

        when(ciudadRepository.findAll()).thenReturn(List.of(ciudadExistente));

        // No hay comunas
        when(comunaRepository.findAll()).thenReturn(List.of());

        // Simular creación de comuna
        ComunaModelo comunaCreada = new ComunaModelo();
        comunaCreada.setIdComuna(99);
        comunaCreada.setNomComuna("Ñuñoa");
        comunaCreada.setCiudad(ciudadExistente);

        when(comunaRepository.save(any(ComunaModelo.class))).thenReturn(comunaCreada);

        when(direccionRepository.save(any(DireccionModelo.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(usuarioRepository.save(any(UsuarioModelo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioModelo resultado = usuarioService.registrarUsuarioCompleto(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pepe", resultado.getNombreUsur());

        verify(ciudadRepository, never()).save(any(CiudadModelo.class));
        verify(comunaRepository, times(1)).save(any(ComunaModelo.class));
        verify(direccionRepository, times(1)).save(any(DireccionModelo.class));
        verify(usuarioRepository, times(1)).save(any(UsuarioModelo.class));
    }




    @Test
    void testRegistrarUsuarioCompleto_direccionSeGuardaCorrectamente() {
        // Arrange
        RegistroUsuarioCompletoDTO dto = new RegistroUsuarioCompletoDTO();
        dto.setRutUsur("11111111-1");
        dto.setNombreUsur("Pepe");
        dto.setApellidoUsur("Jara");
        dto.setCorreoUsur("pepe@correo.cl");
        dto.setNombreCiudad("Santiago");
        dto.setNombreComuna("Ñuñoa");
        dto.setNombDireccion("Calle Falsa");
        dto.setNumDireccion(123);

        // Ciudad existente
        CiudadModelo ciudadExistente = new CiudadModelo();
        ciudadExistente.setIdCiudad(1);
        ciudadExistente.setNombCiudad("Santiago");

        // Comuna existente
        ComunaModelo comunaExistente = new ComunaModelo();
        comunaExistente.setIdComuna(1);
        comunaExistente.setNomComuna("Ñuñoa");
        comunaExistente.setCiudad(ciudadExistente);

        when(ciudadRepository.findAll()).thenReturn(List.of(ciudadExistente));
        when(comunaRepository.findAll()).thenReturn(List.of(comunaExistente));

        // Simular que dirección devuelve la misma que se guarda
        when(direccionRepository.save(any(DireccionModelo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(usuarioRepository.save(any(UsuarioModelo.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UsuarioModelo resultado = usuarioService.registrarUsuarioCompleto(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals("Pepe", resultado.getNombreUsur());

        // Verificar que se guardó dirección con los datos correctos
        verify(direccionRepository).save(argThat(direccion ->
                direccion.getNombDireccion().equals("Calle Falsa") &&
                        direccion.getNumDireccion() == 123 &&
                        direccion.getComuna().getNomComuna().equals("Ñuñoa")
        ));

        verify(usuarioRepository, times(1)).save(any(UsuarioModelo.class));
    }

}