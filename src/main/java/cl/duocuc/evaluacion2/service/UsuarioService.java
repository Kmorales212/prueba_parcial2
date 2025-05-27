package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioDTO;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UsuarioService {

    @Autowired
    private UsuarioService usuarioRepository;
    @Autowired
    private DireccionRepository direccionRepository;


    public List<UsuarioModelo> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModelo> findById(String id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModelo save(UsuarioModelo direccion) {
        return usuarioRepository.save(direccion);
    }

    public void deleteById(String rut) {
        usuarioRepository.deleteById(rut);
    }

    public UsuarioModelo registrarUsuarioConDireccion(RegistroUsuarioDTO dto) {
        // Crear la dirección primero
        DireccionModelo direccion = new DireccionModelo();
        direccion.setNombDireccion(dto.getNombDireccion());
        direccion.setNumDireccion(dto.getNumDireccion());
        direccion = direccionRepository.save(direccion); // debe inyectarse el repositorio

        // Crear el usuario y asociar dirección
        UsuarioModelo usuario = new UsuarioModelo();
        usuario.setRutUsur(dto.getRutUsur());
        usuario.setNombreUsur(dto.getNombreUsur());
        usuario.setApellidoUsur(dto.getApellidoUsur());
        usuario.setCorreoUsur(dto.getCorreoUsur());
        usuario.setDireccion(direccion);

        return usuarioRepository.save(usuario);
    }
}