package cl.duocuc.evaluacion2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

//EliezerCarrasco
@Entity
@Table(name = "comuna")
public class ComunaModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComuna;

    @Column(name = "nom_comuna")
    private String nomComuna;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ciudad")
    @JsonBackReference("ciudad-comuna")
    private CiudadModelo ciudad;

    @OneToMany(mappedBy = "comuna", cascade = CascadeType.ALL)
    @JsonManagedReference("direccion-comuna")
    private List<DireccionModelo> direcciones;


    public Integer getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Integer idComuna) {
        this.idComuna = idComuna;
    }

    public String getNomComuna() {
        return nomComuna;
    }

    public void setNomComuna(String nomComuna) {
        this.nomComuna = nomComuna;
    }

    public CiudadModelo getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadModelo ciudad) {
        this.ciudad = ciudad;
    }

    public List<DireccionModelo> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionModelo> direcciones) {
        this.direcciones = direcciones;
    }
}