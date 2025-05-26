package cl.duocuc.evaluacion2.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class EnvioModel {

   /* @Id
    private String idEnvio;
    private LocalDate fechaEnvio;


    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;

    @OneToOne(cascade = CascadeType.ALL)
    private DireccionModel direccionEntrega;*/
}
