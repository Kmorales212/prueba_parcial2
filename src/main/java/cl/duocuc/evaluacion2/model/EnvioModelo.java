package cl.duocuc.evaluacion2.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "envio")
public class EnvioModelo {

    @Id
    @Column(name = "id_envio")
    private String idEnvio;

    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEnvio estado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion")
    private DireccionModelo direccionEntrega;

    @ManyToOne
    @JoinColumn(name = "id_ruta")
    private RutaModel ruta;

}
