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

// EliezerCarrasco
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

    // Listar todos los usuarios
    public List<UsuarioModelo> findAll() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por RUT
    public Optional<UsuarioModelo> findById(String rut) {
        return usuarioRepository.findById(rut);
    }

    // Guardar usuario simple
    public UsuarioModelo save(UsuarioModelo usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario por RUT
    public void deleteById(String rut) {
        usuarioRepository.deleteById(rut);
    }

    // Registrar usuario con dirección, comuna y ciudad (todo completo)
    public UsuarioModelo registrarUsuarioCompleto(RegistroUsuarioCompletoDTO dto) {
            // Crear el usuario
            UsuarioModelo usuario = new UsuarioModelo();
            usuario.setRutUsur(dto.getRutUsur());
            usuario.setNombreUsur(dto.getNombreUsur());
            usuario.setApellidoUsur(dto.getApellidoUsur());
            usuario.setCorreoUsur(dto.getCorreoUsur());

            // Buscar o crear Ciudad
            CiudadModelo ciudad = ciudadRepository.findAll()
                    .stream()
                    .filter(c -> c.getNombCiudad().equalsIgnoreCase(dto.getNombreCiudad()))
                    .findFirst()
                    .orElseGet(() -> {
                        CiudadModelo nuevaCiudad = new CiudadModelo();
                        nuevaCiudad.setNombCiudad(dto.getNombreCiudad());
                        return ciudadRepository.save(nuevaCiudad);
                    });

            // Buscar o crear Comuna
            ComunaModelo comuna = comunaRepository.findAll()
                    .stream()
                    .filter(c -> c.getNomComuna().equalsIgnoreCase(dto.getNombreComuna()))
                    .findFirst()
                    .orElseGet(() -> {
                        ComunaModelo nuevaComuna = new ComunaModelo();
                        nuevaComuna.setNomComuna(dto.getNombreComuna());
                        nuevaComuna.setCiudad(ciudad); // Aquí se asocia la comuna con la ciudad
                        return comunaRepository.save(nuevaComuna);
                    });

            // Crear la dirección
            DireccionModelo direccion = new DireccionModelo();
            direccion.setNombDireccion(dto.getNombDireccion());
            direccion.setNumDireccion(dto.getNumDireccion());
            direccion.setComuna(comuna);

            // Guardar la dirección primero
            direccionRepository.save(direccion);

            // Asociar dirección al usuario
            usuario.setDireccion(direccion);

            // Guardar el usuario
            return usuarioRepository.save(usuario);
        }

    }

