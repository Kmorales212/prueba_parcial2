package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioCompletoDTO;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping("/listarTodas")
    public ResponseEntity<List<UsuarioModelo>> listarTodas() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // Obtener un usuario por RUT
    @GetMapping("/obtenerPorRut/{rut}")
    public ResponseEntity<UsuarioModelo> obtenerPorRut(@PathVariable String rut) {
        return usuarioService.findById(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un usuario simple
    @PostMapping("/crear")
    public ResponseEntity<UsuarioModelo> crear(@RequestBody UsuarioModelo usuario) {
        UsuarioModelo creado = usuarioService.save(usuario);
        return ResponseEntity.ok(creado);
    }

    // Actualizar un usuario existente
    @PutMapping("/actualizar/{rut}")
    public ResponseEntity<UsuarioModelo> actualizar(@PathVariable String rut, @RequestBody UsuarioModelo usuario) {
        return usuarioService.findById(rut).map(u -> {
            usuario.setRutUsur(rut);
            UsuarioModelo actualizado = usuarioService.save(usuario);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un usuario
    @DeleteMapping("/eliminar/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        if (usuarioService.findById(rut).isPresent()) {
            usuarioService.deleteById(rut);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Registrar un usuario completo con dirección, comuna y ciudad
    @PostMapping("/registrar-completo")
    public ResponseEntity<UsuarioModelo> registrarUsuarioCompleto(@RequestBody RegistroUsuarioCompletoDTO dto) {
        UsuarioModelo usuario = usuarioService.registrarUsuarioCompleto(dto);
        return ResponseEntity.ok(usuario);
    }
}