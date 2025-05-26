package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository<CiudadModelo, Integer> {}

