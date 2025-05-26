package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ComunaModelo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_comuna;

    @Column(nullable = false)
    private String nomb_comuna;
}
