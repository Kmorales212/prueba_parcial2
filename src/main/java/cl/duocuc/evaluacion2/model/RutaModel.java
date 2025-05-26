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
   private List<EnvioModelo> envios;

   @OneToOne(cascade = CascadeType.ALL)
   private DireccionModelo direccionInicio;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionModelo direccionDestino;

    @ManyToOne
    private CiudadModelo ciudad;

    @Enumerated(EnumType.STRING)
    private EstadoRuta estado;

    private String descripcion;


}