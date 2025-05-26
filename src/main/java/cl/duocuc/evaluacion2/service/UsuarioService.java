package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.UsuarioModelo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioModelo> findAll() {
        return usuarioRepository.findAll();
    }

    public UsuarioModelo findByrut(String rut ) {
        return usuarioRepository.findByRut(rut);
    }

    public UsuarioModelo save(UsuarioModelo usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(UsuarioModelo usuario) {
        usuarioRepository.delete(usuario);
    }
}
