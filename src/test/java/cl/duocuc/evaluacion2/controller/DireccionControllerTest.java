package cl.duocuc.evaluacion2.controller;

import cl.duocuc.evaluacion2.controller.eliezer.DireccionController;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.service.DireccionService;
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

@WebMvcTest(DireccionController.class)
public class DireccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DireccionService direccionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodas() throws Exception {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");
        direccion.setNumDireccion(123);

        when(direccionService.findAll()).thenReturn(List.of(direccion));

        mockMvc.perform(get("/api/direcciones/listarTodas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombDireccion").value("Calle Falsa"));
    }

    @Test
    void testObtenerPorId_Existe() throws Exception {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");

        when(direccionService.findById(1)).thenReturn(Optional.of(direccion));

        mockMvc.perform(get("/api/direcciones/obtenerPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombDireccion").value("Calle Falsa"));
    }

    @Test
    void testObtenerPorId_NoExiste() throws Exception {
        when(direccionService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/direcciones/obtenerPorId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearDireccion() throws Exception {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");
        direccion.setNumDireccion(123);

        when(direccionService.save(any(DireccionModelo.class))).thenReturn(direccion);

        mockMvc.perform(post("/api/direcciones/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombDireccion").value("Calle Falsa"));
    }

    @Test
    void testActualizarDireccion_Existe() throws Exception {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");

        when(direccionService.findById(1)).thenReturn(Optional.of(direccion));
        when(direccionService.save(any(DireccionModelo.class))).thenReturn(direccion);

        mockMvc.perform(put("/api/direcciones/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombDireccion").value("Calle Falsa"));
    }

    @Test
    void testActualizarDireccion_NoExiste() throws Exception {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");

        when(direccionService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/direcciones/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(direccion)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarDireccion_Existe() throws Exception {
        DireccionModelo direccion = new DireccionModelo();
        direccion.setIdDireccion(1);
        direccion.setNombDireccion("Calle Falsa");

        when(direccionService.findById(1)).thenReturn(Optional.of(direccion));

        mockMvc.perform(delete("/api/direcciones/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarDireccion_NoExiste() throws Exception {
        when(direccionService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/direcciones/eliminar/1"))
                .andExpect(status().isNotFound());
    }
}
