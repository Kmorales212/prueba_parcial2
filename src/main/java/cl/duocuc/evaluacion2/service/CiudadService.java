package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public List<CiudadModelo> findAll() {
        return ciudadRepository.findAll();
    }

    public Optional<CiudadModelo> findById(int id) {
        return ciudadRepository.findById(id);
    }

    public CiudadModelo save(CiudadModelo ciudad) {
        return ciudadRepository.save(ciudad);
    }

    public void deleteById(int id) {
        ciudadRepository.deleteById(id);
    }
}