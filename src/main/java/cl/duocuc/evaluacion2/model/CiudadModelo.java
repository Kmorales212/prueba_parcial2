package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Ciudad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiudadModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_ciudad;

    @Column(nullable = false)
    private String nomb_ciudad;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
    private List<ComunaModelo> comunas;

}
