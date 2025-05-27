package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionModelo, Integer> {
}
