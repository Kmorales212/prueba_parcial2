package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    @Autowired
    private UsuarioService usuarioRepository;

    public List<UsuarioModelo> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModelo> findById(int id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModelo save(UsuarioModelo direccion) {
        return usuarioRepository.save(direccion);
    }

    public void deleteById(int id) {
        usuarioRepository.deleteById(id);
    }
}
