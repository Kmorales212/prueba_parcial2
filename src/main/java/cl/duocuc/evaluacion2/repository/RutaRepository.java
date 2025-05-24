package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.RutaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<RutaModel, String> {

}
