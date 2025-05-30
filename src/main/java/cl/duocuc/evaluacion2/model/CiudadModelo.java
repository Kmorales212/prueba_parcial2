package cl.duocuc.evaluacion2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

//EliezerCarrasco
@Entity
@Table(name = "ciudad")
public class CiudadModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudad;

    @Column(name = "nomb_ciudad")
    private String nombCiudad;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
    @JsonManagedReference("ciudad-comuna")
    private List<ComunaModelo> comunas;


    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombCiudad() {
        return nombCiudad;
    }

    public void setNombCiudad(String nombCiudad) {
        this.nombCiudad = nombCiudad;
    }

    public List<ComunaModelo> getComunas() {
        return comunas;
    }

    public void setComunas(List<ComunaModelo> comunas) {
        this.comunas = comunas;
    }
}