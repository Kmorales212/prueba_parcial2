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
    private String rut_usur;

    @Column(nullable = false)
    private String nombre_usur;

    @Column(nullable = false)
    private String apellido_usur;

    @Column(nullable = false)
    private String correo_usur;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private DireccionModelo direccion;

}
