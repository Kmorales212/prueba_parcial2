package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.UsuarioModel;
import cl.duocuc.evaluacion2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepository usuarioRepo;

    public UsuarioModel crearUsuario(UsuarioModel usuario) {
        return usuarioRepo.save(usuario);
    }

    public List<UsuarioModel> obtenerTodos() {
        return usuarioRepo.findAll();
    }
}
