package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel,Integer> {
}
