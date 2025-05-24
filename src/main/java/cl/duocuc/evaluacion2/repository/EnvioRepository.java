package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.EnvioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvioRepository extends JpaRepository<EnvioModel, String> {
}