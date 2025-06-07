package cl.duocuc.evaluacion2.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import cl.duocuc.evaluacion2.controller.kevin.EnvioController;
import cl.duocuc.evaluacion2.model.*;
import cl.duocuc.evaluacion2.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Autowired
    private ObjectMapper objectMapper;

    private EnvioModelo envioModelo;

    @BeforeEach
    void setUp(){
        envioModelo = new EnvioModelo();
        envioModelo.setIdEnvio("ENV-001");
        envioModelo.setFechaEnvio(LocalDate.of(2025, 6, 7));
        envioModelo.setEstado(EstadoEnvio.valueOf("PENDIENTE"));
    }
}
