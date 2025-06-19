package cl.duocuc.evaluacion2.controller;

import cl.duocuc.evaluacion2.controller.eliezer.UsuarioController;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.service.UsuarioService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarTodas() throws Exception {
        UsuarioModelo usuario = new UsuarioModelo();
        usuario.setRutUsur("12345678-9");
        usuario.setNombreUsur("Juan");

        Mockito.when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios/listarTodas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rutUsur").value("12345678-9"));
    }

    @Test
    void testObtenerPorRut() throws Exception {
        UsuarioModelo usuario = new UsuarioModelo();
        usuario.setRutUsur("12345678-9");
        usuario.setNombreUsur("Juan");

        Mockito.when(usuarioService.findById("12345678-9")).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/obtenerPorRut/12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutUsur").value("12345678-9"));
    }

    @Test
    void testObtenerPorRut_NotFound() throws Exception {
        Mockito.when(usuarioService.findById("99999999-9")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/obtenerPorRut/99999999-9"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearUsuario() throws Exception {
        UsuarioModelo nuevoUsuario = new UsuarioModelo();
        nuevoUsuario.setRutUsur("11111111-1");
        nuevoUsuario.setNombreUsur("Nuevo");
        nuevoUsuario.setApellidoUsur("Apellido");
        nuevoUsuario.setCorreoUsur("nuevo@correo.com");

        Mockito.when(usuarioService.save(Mockito.any())).thenReturn(nuevoUsuario);

        mockMvc.perform(post("/api/usuarios/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutUsur").value("11111111-1"));
    }


    @Test
    void testActualizarUsuario() throws Exception {
        String rut = "12345678-9";

        UsuarioModelo existente = new UsuarioModelo();
        existente.setRutUsur(rut);
        existente.setNombreUsur("Original");

        UsuarioModelo actualizado = new UsuarioModelo();
        actualizado.setRutUsur(rut);
        actualizado.setNombreUsur("NombreActualizado");
        actualizado.setApellidoUsur("Apellido");
        actualizado.setCorreoUsur("correo@actualizado.com");

        Mockito.when(usuarioService.findById(rut)).thenReturn(Optional.of(existente));
        Mockito.when(usuarioService.save(Mockito.any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuarios/actualizar/" + rut)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreUsur").value("NombreActualizado"));
    }


    @Test
    void testEliminarUsuario() throws Exception {
        UsuarioModelo existente = new UsuarioModelo();
        existente.setRutUsur("12345678-9");

        Mockito.when(usuarioService.findById("12345678-9")).thenReturn(Optional.of(existente));

        mockMvc.perform(delete("/api/usuarios/eliminar/12345678-9"))
                .andExpect(status().isNoContent());
    }


    @Test
    void testEliminarUsuario_NotFound() throws Exception {
        Mockito.when(usuarioService.findById("99999999-9")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/usuarios/eliminar/99999999-9"))
                .andExpect(status().isNotFound());
    }

}
