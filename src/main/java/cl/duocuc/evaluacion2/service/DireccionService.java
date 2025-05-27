package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//EliezerCarrasco
@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public List<DireccionModelo> findAll() {
        return direccionRepository.findAll();
    }

    public Optional<DireccionModelo> findById(int id) {
        return direccionRepository.findById(id);
    }

    public DireccionModelo save(DireccionModelo direccion) {
        return direccionRepository.save(direccion);
    }

    public void deleteById(int id) {
        direccionRepository.deleteById(id);
    }
}