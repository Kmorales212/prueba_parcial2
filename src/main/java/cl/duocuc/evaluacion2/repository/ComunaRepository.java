package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//EliezerCarrasco
@Repository
public interface ComunaRepository extends JpaRepository<ComunaModelo, Integer> {}