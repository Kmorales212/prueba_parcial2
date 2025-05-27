package cl.duocuc.evaluacion2.dto;

import lombok.Data;

@Data
public class RegistroUsuarioDTO {

    private String rutUsur;
    private String nombreUsur;
    private String apellidoUsur;
    private String correoUsur;

    private String nombDireccion;
    private int numDireccion;
}
