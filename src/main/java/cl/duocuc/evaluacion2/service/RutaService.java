package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    public List<RutaModel> getAllRutas() {
        return rutaRepository.findAll();
    }

    public Optional<RutaModel> getRutaById(String id) {
        return rutaRepository.findById(id);
    }

    public RutaModel createRuta(RutaModel ruta) {
        return rutaRepository.save(ruta);
    }

    public Optional<RutaModel> updateRuta(String id, RutaModel rutaDetails) {
        Optional<RutaModel> optionalRuta = rutaRepository.findById(id);
        if (optionalRuta.isEmpty()) {
            return Optional.empty();
        }
        RutaModel ruta = optionalRuta.get();
        ruta.setFechaInicio(rutaDetails.getFechaInicio());
        ruta.setEnvios(rutaDetails.getEnvios());
        ruta.setDireccionInicio(rutaDetails.getDireccionInicio());
        ruta.setDireccionDestino(rutaDetails.getDireccionDestino());
        ruta.setCiudad(rutaDetails.getCiudad());
        ruta.setEstado(rutaDetails.getEstado());
        ruta.setDescripcion(rutaDetails.getDescripcion());

        return Optional.of(rutaRepository.save(ruta));
    }

    public boolean deleteRuta(String id) {
        if (!rutaRepository.existsById(id)) {
            return false;
        }
        rutaRepository.deleteById(id);
        return true;
    }


}