package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//EliezerCarrasco
@Repository
public interface CiudadRepository extends JpaRepository<CiudadModelo, Integer> {}