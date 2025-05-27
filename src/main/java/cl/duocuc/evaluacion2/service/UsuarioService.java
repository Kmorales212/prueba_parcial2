package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioCompletoDTO;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import cl.duocuc.evaluacion2.repository.ComunaRepository;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import cl.duocuc.evaluacion2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//EliezerCarrasco
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private ComunaRepository comunaRepository;



    public List<UsuarioModelo> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModelo> findById(String rut) {
        return usuarioRepository.findById(rut);
    }

    public UsuarioModelo save(UsuarioModelo usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deleteById(String rut) {
        usuarioRepository.deleteById(rut);
    }

    // Registrar usuario con dirección, comuna y ciudad (todo completo)
    public UsuarioModelo registrarUsuarioCompleto(RegistroUsuarioCompletoDTO dto) {
        CiudadModelo ciudad = ciudadRepository.findAll().stream()
                .filter(c -> c.getNombCiudad().equalsIgnoreCase(dto.getNombreCiudad()))
                .findFirst()
                .orElseGet(() -> {
                    CiudadModelo nuevaCiudad = new CiudadModelo();
                    nuevaCiudad.setNombCiudad(dto.getNombreCiudad());
                    return ciudadRepository.save(nuevaCiudad);
                });

        ComunaModelo comuna = comunaRepository.findAll().stream()
                .filter(c -> c.getNomComuna().equalsIgnoreCase(dto.getNombreComuna()))
                .findFirst()
                .orElseGet(() -> {
                    ComunaModelo nuevaComuna = new ComunaModelo();
                    nuevaComuna.setNomComuna(dto.getNombreComuna());
                    nuevaComuna.setCiudad(ciudad);
                    return comunaRepository.save(nuevaComuna);
                });

        DireccionModelo direccion = new DireccionModelo();
        direccion.setNombDireccion(dto.getNombDireccion());
        direccion.setNumDireccion(dto.getNumDireccion());
        direccion.setComuna(comuna);
        direccion = direccionRepository.save(direccion);

        UsuarioModelo usuario = new UsuarioModelo();
        usuario.setRutUsur(dto.getRutUsur());
        usuario.setNombreUsur(dto.getNombreUsur());
        usuario.setApellidoUsur(dto.getApellidoUsur());
        usuario.setCorreoUsur(dto.getCorreoUsur());
        usuario.setDireccion(direccion);

        return usuarioRepository.save(usuario);
    }
}