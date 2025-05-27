package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Comuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComuna;

    @Column(nullable = false)
    private String nomComuna;

    @OneToMany(mappedBy = "comuna", cascade = CascadeType.ALL)
    private List<DireccionModelo> direcciones;


}
