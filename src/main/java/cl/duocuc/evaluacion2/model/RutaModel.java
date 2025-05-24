package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
public class RutaModel {

    @Id
    private String idRuta;

    private LocalDateTime fechaInicio;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EnvioModel> envios;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionModel direccionInicio;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionModel direccionDestino;

    @ManyToOne
    private CiudadModel ciudad;

    @Enumerated(EnumType.STRING)
    private EstadoRuta estado;

    private String descripcion;


}