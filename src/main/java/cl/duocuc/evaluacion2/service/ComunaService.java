package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.repository.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<ComunaModelo> findAll() {
        return comunaRepository.findAll();
    }

    public Optional<ComunaModelo> findById(int id) {
        return comunaRepository.findById(id);
    }
    public ComunaModelo save(ComunaModelo comuna) {
        return comunaRepository.save(comuna);
    }

    public void deleteById(int id) {
        comunaRepository.deleteById(id);
    }
}