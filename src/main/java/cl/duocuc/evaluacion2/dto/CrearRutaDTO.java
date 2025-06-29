package cl.duocuc.evaluacion2.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class CrearRutaDTO {
    private String idRuta;
    private LocalDateTime fechaInicio;
    private String ciudadId;
    private String descripcion;
    private Integer idDireccionInicio;
    private Integer idDireccionDestino;
    private List<String> idsEnvios;
}
