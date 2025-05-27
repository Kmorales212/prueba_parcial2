package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioCompletoDTO;
import cl.duocuc.evaluacion2.dto.RegistroUsuarioDTO;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioModelo>> listarTodos() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{rut}")
    public ResponseEntity<UsuarioModelo> obtenerPorRut(@PathVariable String rut) {
        return usuarioService.findById(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioModelo> crear(@RequestBody UsuarioModelo usuario) {
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    @PutMapping("/{rut}")
    public ResponseEntity<UsuarioModelo> actualizar(@PathVariable String rut, @RequestBody UsuarioModelo usuario) {
        return usuarioService.findById(rut).map(u -> {
            usuario.setRutUsur(rut);
            return ResponseEntity.ok(usuarioService.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        usuarioService.deleteById(rut);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/registrar-con-direccion")
    public ResponseEntity<UsuarioModelo> registrarConDireccion(@RequestBody RegistroUsuarioDTO dto) {
        UsuarioModelo nuevoUsuario = usuarioService.registrarUsuarioConDireccion(dto);
        return ResponseEntity.ok(nuevoUsuario);
    }
    @PostMapping("/registrar-completo")
    public ResponseEntity<UsuarioModelo> registrarUsuarioCompleto(@RequestBody RegistroUsuarioCompletoDTO dto) {
        UsuarioModelo usuario = usuarioService.registrarUsuarioCompleto(dto);
        return ResponseEntity.ok(usuario);
    }
}