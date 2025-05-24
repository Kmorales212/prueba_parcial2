package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;

@Entity
public class DireccionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;
    private String calleDireccion;
    private String numeroDireccion;

    @ManyToOne
    private CiudadModel ciudad;
}
