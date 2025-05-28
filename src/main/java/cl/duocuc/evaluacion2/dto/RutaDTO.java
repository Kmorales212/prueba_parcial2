package cl.duocuc.evaluacion2.dto;

import java.time.LocalDateTime;
import java.util.List;
import cl.duocuc.evaluacion2.model.EstadoRuta;
import lombok.Data;

@Data
public class RutaDTO {
    private String idRuta;
    private LocalDateTime fechaInicio;
    private String ciudadNombre;
    private EstadoRuta estado;
    private String descripcion;
    private List<String> idsEnvios;


}