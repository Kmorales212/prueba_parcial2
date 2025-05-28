package cl.duocuc.evaluacion2.dto;

import java.time.LocalDate;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import lombok.Data;

@Data
public class CrearEnvioDTO {
    private String idEnvio;
    private LocalDate fechaEnvio;
    private DireccionModelo direccionEntrega;


}
