package cl.duocuc.evaluacion2.dto;

import lombok.Data;

@Data
public class UsuarioConDireccionDTO {
    private String rutUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String correoUsuario;

    private String nombreDireccion;
    private int numeroDireccion;
    private int idComuna;
}
