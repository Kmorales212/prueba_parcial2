package cl.duocuc.evaluacion2.dto;

import java.time.LocalDate;
import cl.duocuc.evaluacion2.model.EstadoEnvio;
import lombok.Data;

@Data
public class EnvioDTO {
    private String idEnvio;
    private LocalDate fechaEnvio;
    private EstadoEnvio estado;
    private String direccionEntregaResumen;
}