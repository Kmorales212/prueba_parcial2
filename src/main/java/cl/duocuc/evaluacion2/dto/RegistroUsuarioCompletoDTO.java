package cl.duocuc.evaluacion2.dto;

import lombok.Data;

@Data
public class RegistroUsuarioCompletoDTO {
    // Datos usuario
    private String rutUsur;
    private String nombreUsur;
    private String apellidoUsur;
    private String correoUsur;

    // Datos dirección
    private String nombDireccion;
    private int numDireccion;

    // Datos comuna y ciudad
    private String nombreComuna;
    private String nombreCiudad;
}