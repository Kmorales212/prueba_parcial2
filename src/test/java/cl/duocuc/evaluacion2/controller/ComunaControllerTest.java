package cl.duocuc.evaluacion2.controller;

import cl.duocuc.evaluacion2.controller.eliezer.ComunaController;
import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.service.ComunaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(ComunaController.class)
public class ComunaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComunaService comunaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodas() throws Exception {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaService.findAll()).thenReturn(List.of(comuna));

        mockMvc.perform(get("/api/comunas/listarTodas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomComuna").value("Ñuñoa"));
    }

    @Test
    void testObtenerPorId_Existe() throws Exception {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaService.findById(1)).thenReturn(Optional.of(comuna));

        mockMvc.perform(get("/api/comunas/obtenerPorId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomComuna").value("Ñuñoa"));
    }

    @Test
    void testObtenerPorId_NoExiste() throws Exception {
        when(comunaService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/comunas/obtenerPorId/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearComuna() throws Exception {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaService.save(any(ComunaModelo.class))).thenReturn(comuna);

        mockMvc.perform(post("/api/comunas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomComuna").value("Ñuñoa"));
    }

    @Test
    void testActualizarComuna_Existe() throws Exception {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaService.findById(1)).thenReturn(Optional.of(comuna));
        when(comunaService.save(any(ComunaModelo.class))).thenReturn(comuna);

        mockMvc.perform(put("/api/comunas/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomComuna").value("Ñuñoa"));
    }

    @Test
    void testActualizarComuna_NoExiste() throws Exception {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/comunas/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarComuna_Existe() throws Exception {
        ComunaModelo comuna = new ComunaModelo();
        comuna.setIdComuna(1);
        comuna.setNomComuna("Ñuñoa");

        when(comunaService.findById(1)).thenReturn(Optional.of(comuna));

        mockMvc.perform(delete("/api/comunas/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarComuna_NoExiste() throws Exception {
        when(comunaService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/comunas/eliminar/1"))
                .andExpect(status().isNotFound());
    }
}
