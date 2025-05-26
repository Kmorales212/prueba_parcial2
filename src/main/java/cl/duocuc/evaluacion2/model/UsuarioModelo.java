package cl.duocuc.evaluacion2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModelo {
    @Id
    private String rutUsur;

    @Column(nullable = false)
    private String nombreUsur;

    @Column(nullable = false)
    private String apellidoUsur;

    @Column(nullable = false)
    private String correoUsur;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private DireccionModelo direccion;

}
