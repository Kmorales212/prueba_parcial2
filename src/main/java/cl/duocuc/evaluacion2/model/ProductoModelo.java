package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "envio_id")
    private EnvioModelo envio;
}