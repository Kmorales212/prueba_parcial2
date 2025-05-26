package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.UsuarioModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModelo, Long> {

    List<UsuarioModelo> findByNombre(String nombre);

    UsuarioModelo findByRut(String rut);
    UsuarioModelo findByApellido(String apellido);
    UsuarioModelo findByCorreo(String correo);


    @Query("SELECT u FROM UsuarioModelo u WHERE u.rut= :rut")
    List<UsuarioModelo> BuscarPorRut(@Param("rut") String rut);

    @Query(value = "SELECT * FROM UsuarioModelo WHERE correo = :correo", nativeQuery = true)
    UsuarioModelo buscarPorCorreo(@Param("correo") String correo);
}
