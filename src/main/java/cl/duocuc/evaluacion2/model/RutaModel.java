package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "ruta")
public class RutaModel {

    @Id
    @Column(name = "id_ruta")
    private String idRuta;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnvioModelo> envios;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_inicio")
    private DireccionModelo direccionInicio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_destino")
    private DireccionModelo direccionDestino;

    @ManyToOne
    @JoinColumn(name = "id_ciudad")
    private CiudadModelo ciudad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoRuta estado;

    @Column(name = "descripcion")
    private String descripcion;

}
