package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Direccion")
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

    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL)
    private List<UsuarioModelo> usuarios;
}