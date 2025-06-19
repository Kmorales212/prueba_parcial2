package cl.duocuc.evaluacion2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

//EliezerCarrasco
@Entity
@Table(name = "direccion")
public class DireccionModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDireccion;

    @Column(name = "nomb_direccion")
    private String nombDireccion;

    @Column(name = "num_direccion")
    private Integer numDireccion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_comuna")
    @JsonBackReference("direccion-comuna")
    private ComunaModelo comuna;

    @ManyToOne
    @JsonBackReference("usuario-direccion")
    private UsuarioModelo usuario;


    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getNombDireccion() {
        return nombDireccion;
    }

    public void setNombDireccion(String nombDireccion) {
        this.nombDireccion = nombDireccion;
    }

    public Integer getNumDireccion() {
        return numDireccion;
    }

    public void setNumDireccion(Integer numDireccion) {
        this.numDireccion = numDireccion;
    }

    public ComunaModelo getComuna() {
        return comuna;
    }

    public void setComuna(ComunaModelo comuna) {
        this.comuna = comuna;
    }
}