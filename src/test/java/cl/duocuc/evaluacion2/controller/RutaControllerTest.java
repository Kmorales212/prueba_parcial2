package cl.duocuc.evaluacion2.controller;

import cl.duocuc.evaluacion2.controller.kevin.RutaController;
import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.model.*;
import cl.duocuc.evaluacion2.service.RutaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RutaController.class)
public class RutaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RutaService rutaService;

    @Autowired
    private ObjectMapper objectMapper;

    private RutaModel ruta;

    @BeforeEach
    void setUp() {
        ruta = new RutaModel();
        ruta.setIdRuta("RUTA-001");
        ruta.setFechaInicio(LocalDateTime.of(2025, 6, 8, 0, 0));
        ruta.setDescripcion("Ruta prueba");
        ruta.setEstado(EstadoRuta.PENDIENTE);

        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setNombCiudad("Santiago");
        ruta.setCiudad(ciudad);

        EnvioModelo envio = new EnvioModelo();
        envio.setIdEnvio("ENV-001");
        ruta.setEnvios(List.of(envio));
    }

    @Test
    void testCrearRuta() throws Exception {
        CrearRutaDTO dto = new CrearRutaDTO();
        dto.setIdRuta("RUTA-001");
        dto.setFechaInicio(ruta.getFechaInicio());
        dto.setDescripcion("Ruta prueba");
        dto.setCiudadId("1");
        dto.setDireccionInicio(new DireccionModelo());
        dto.setDireccionDestino(new DireccionModelo());
        dto.setIdsEnvios(List.of("ENV-001"));

        when(rutaService.crearRuta(any())).thenReturn(ruta);

        mockMvc.perform(post("/api/rutas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRuta").value("RUTA-001"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void testListarRutas() throws Exception {
        when(rutaService.getAllRutas()).thenReturn(List.of(ruta));

        mockMvc.perform(get("/api/rutas/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idRuta").value("RUTA-001"));
    }

    @Test
    void testEliminarRuta() throws Exception {
        mockMvc.perform(delete("/api/rutas/eliminar/RUTA-001"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarEstado() throws Exception {
        ruta.setEstado(EstadoRuta.COMPLETADA);
        when(rutaService.actualizarEstado(eq("RUTA-001"), eq(EstadoRuta.COMPLETADA))).thenReturn(ruta);

        mockMvc.perform(put("/api/rutas/estado/RUTA-001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("estado", "COMPLETADA"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("COMPLETADA"));
    }
}
