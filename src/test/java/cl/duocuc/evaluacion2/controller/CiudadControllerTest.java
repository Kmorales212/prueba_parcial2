package cl.duocuc.evaluacion2.controller;

import cl.duocuc.evaluacion2.controller.eliezer.CiudadController;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.service.CiudadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CiudadController.class)
public class CiudadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CiudadService ciudadService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodas() throws Exception {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadService.findAll()).thenReturn(List.of(ciudad));

        mockMvc.perform(get("/api/ciudades/listarTodas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombCiudad").value("Santiago"));
    }

    @Test
    void testObtenerPorId_Existe() throws Exception {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadService.findById(1)).thenReturn(Optional.of(ciudad));

        mockMvc.perform(get("/api/ciudades/obtenerPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombCiudad").value("Santiago"));
    }

    @Test
    void testObtenerPorId_NoExiste() throws Exception {
        when(ciudadService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ciudades/obtenerPorId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearCiudad() throws Exception {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadService.save(any(CiudadModelo.class))).thenReturn(ciudad);

        mockMvc.perform(post("/api/ciudades/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ciudad)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombCiudad").value("Santiago"));
    }

    @Test
    void testActualizarCiudad_Existe() throws Exception {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadService.findById(1)).thenReturn(Optional.of(ciudad));
        when(ciudadService.save(any(CiudadModelo.class))).thenReturn(ciudad);

        mockMvc.perform(put("/api/ciudades/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ciudad)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombCiudad").value("Santiago"));
    }

    @Test
    void testActualizarCiudad_NoExiste() throws Exception {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/ciudades/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ciudad)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCiudad_Existe() throws Exception {
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(1);
        ciudad.setNombCiudad("Santiago");

        when(ciudadService.findById(1)).thenReturn(Optional.of(ciudad));

        mockMvc.perform(delete("/api/ciudades/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarCiudad_NoExiste() throws Exception {
        when(ciudadService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/ciudades/eliminar/1"))
                .andExpect(status().isNotFound());
    }
}
