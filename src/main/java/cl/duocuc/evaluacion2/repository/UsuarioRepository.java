package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.UsuarioModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModelo, String> {
}