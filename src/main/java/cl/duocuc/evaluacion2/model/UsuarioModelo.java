package cl.duocuc.evaluacion2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

//EliezerCarrasco
@Entity
@Table(name = "usuario")
public class UsuarioModelo {

    @Id
    @Column(name = "rut_usur")
    private String rutUsur;

    @Column(name = "nombre_usur")
    private String nombreUsur;

    @Column(name = "apellido_usur")
    private String apellidoUsur;

    @Column(name = "correo_usur")
    private String correoUsur;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion")
    @JsonManagedReference("usuario-direccion")
    private DireccionModelo direccion;


    public String getRutUsur() {
        return rutUsur;
    }

    public void setRutUsur(String rutUsur) {
        this.rutUsur = rutUsur;
    }

    public String getNombreUsur() {
        return nombreUsur;
    }

    public void setNombreUsur(String nombreUsur) {
        this.nombreUsur = nombreUsur;
    }

    public String getApellidoUsur() {
        return apellidoUsur;
    }

    public void setApellidoUsur(String apellidoUsur) {
        this.apellidoUsur = apellidoUsur;
    }

    public String getCorreoUsur() {
        return correoUsur;
    }

    public void setCorreoUsur(String correoUsur) {
        this.correoUsur = correoUsur;
    }

    public DireccionModelo getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionModelo direccion) {
        this.direccion = direccion;
    }
}