package cl.duocuc.evaluacion2.repository;


import cl.duocuc.evaluacion2.model.CiudadModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository <CiudadModel, Integer> {
}
