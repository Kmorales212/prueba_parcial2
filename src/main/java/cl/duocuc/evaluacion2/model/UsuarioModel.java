package cl.duocuc.evaluacion2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UsuarioModel {

    @Id
    private String rutUsuario;
    private String nombreUsuario;
    private String apellidoUsuario;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionModel direccion;
}
