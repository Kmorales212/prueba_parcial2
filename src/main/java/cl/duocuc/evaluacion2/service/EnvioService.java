package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

 @Autowired
    private EnvioRepository envioRepository;

    public List<EnvioModelo> getAllEnvios() {
        return envioRepository.findAll();
    }

    public Optional<EnvioModelo> getEnvioById(String id) {
        return envioRepository.findById(id);
    }

    public EnvioModelo createEnvio(EnvioModelo envio) {
        return envioRepository.save(envio);
    }

    public Optional<EnvioModelo> updateEnvio(String id, EnvioModelo envioDetails) {
        Optional<EnvioModelo> optionalEnvio = envioRepository.findById(id);
        if (optionalEnvio.isEmpty()) {
            return Optional.empty();
        }
        EnvioModelo envio = optionalEnvio.get();
        envio.setFechaEnvio(envioDetails.getFechaEnvio());
        envio.setEstado(envioDetails.getEstado());
        envio.setDireccionEntrega(envioDetails.getDireccionEntrega());
        return Optional.of(envioRepository.save(envio));
    }

    public boolean deleteEnvio(String id) {
        if (!envioRepository.existsById(id)) {
            return false;
        }
        envioRepository.deleteById(id);
        return true;
    }


}