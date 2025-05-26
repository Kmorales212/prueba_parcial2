package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CiudadModelo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiudadModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_ciudad;

    @Column(nullable = false)
    private String nomb_ciudad;

}
