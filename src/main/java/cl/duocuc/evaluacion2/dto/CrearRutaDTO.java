package cl.duocuc.evaluacion2.dto;

import java.time.LocalDateTime;
import java.util.List;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import lombok.Data;

@Data
public class CrearRutaDTO {
    private String idRuta;
    private LocalDateTime fechaInicio;
    private String ciudadId;
    private String descripcion;
    private DireccionModelo direccionInicio;
    private DireccionModelo direccionDestino;
    private List<String> idsEnvios;


}