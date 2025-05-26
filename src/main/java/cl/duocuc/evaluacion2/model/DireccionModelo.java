package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DireccionModelo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_direccion;

    @Column(nullable = false)
    private String nomb_direccion;

    @Column(nullable = false)
    private int num_direccion;


}