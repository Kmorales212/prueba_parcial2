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
    private int idDireccion;

    @Column(nullable = false)
    private String nombDireccion;

    @Column(nullable = false)
    private int numDireccion;

    @OneToMany(mappedBy = "direccion", cascade = CascadeType.ALL)
    private List<UsuarioModelo> usuarios;
}