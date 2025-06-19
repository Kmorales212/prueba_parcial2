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
        if (dto.getNombDireccion() == null || dto.getNumDireccion() <= 0) {
            throw new RuntimeException("Datos de dirección inválidos");
        }

        Optional<CiudadModelo> ciudadOpt = ciudadRepository.findAll().stream()
                .filter(c -> c.getNombCiudad().equalsIgnoreCase(dto.getNombreCiudad()))
                .findFirst();

        if (ciudadOpt.isEmpty()) {
            throw new RuntimeException("Ciudad no encontrada");
        }

        CiudadModelo ciudad = ciudadOpt.get();

        Optional<ComunaModelo> comunaOpt = comunaRepository.findAll().stream()
                .filter(c -> c.getNomComuna() != null &&
                        c.getNomComuna().equalsIgnoreCase(dto.getNombreComuna()) &&
                        c.getCiudad() != null &&
                        c.getCiudad().getNombCiudad().equalsIgnoreCase(dto.getNombreCiudad()))
                .findFirst();

        if (comunaOpt.isEmpty()) {
            throw new RuntimeException("Comuna no encontrada");
        }

        ComunaModelo comuna = comunaOpt.get();

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

    // Crear un nuevo usuario simple (sin dirección ni validaciones)
    public UsuarioModelo createUser(UsuarioModelo usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente por RUT
    public Optional<UsuarioModelo> updateUser(String rut, UsuarioModelo nuevoUsuario) {
        return usuarioRepository.findById(rut).map(usuarioExistente -> {
            usuarioExistente.setNombreUsur(nuevoUsuario.getNombreUsur());
            usuarioExistente.setApellidoUsur(nuevoUsuario.getApellidoUsur());
            usuarioExistente.setCorreoUsur(nuevoUsuario.getCorreoUsur());
            usuarioExistente.setDireccion(nuevoUsuario.getDireccion());
            return usuarioRepository.save(usuarioExistente);
        });
    }

    // Eliminar usuario por RUT, retorna true si se eliminó
    public boolean deleteUser(String rut) {
        if (usuarioRepository.existsById(rut)) {
            usuarioRepository.deleteById(rut);
            return true;
        }
        return false;
    }
}
