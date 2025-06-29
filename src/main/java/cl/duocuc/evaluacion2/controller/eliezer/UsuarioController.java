package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioCompletoDTO;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Gestios de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "listar todos los usuarios", description = "este metodo se encarga de listar todos los usuarios existentes en nuestra base de datos")
    @GetMapping("/listarTodas")
    public ResponseEntity<List<UsuarioModelo>> listarTodas() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(summary = "buscar usuarios por id", description = "este metodo se encarga de buscar un usuario mediante su id")
    @GetMapping("/obtenerPorRut/{rut}")
    public ResponseEntity<UsuarioModelo> obtenerPorRut(@PathVariable String rut) {
        return usuarioService.findById(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "crear usuario", description = "este metodo se encarga de crear un usuario sin asignarle una direccion, comuna o ciudad")
    @PostMapping("/crear")
    public ResponseEntity<UsuarioModelo> crear(@RequestBody UsuarioModelo usuario) {
        UsuarioModelo creado = usuarioService.save(usuario);
        return ResponseEntity.ok(creado);
    }

    @Operation(summary = "actualizar usuario por id", description = "este metodo se encarga de actualiuzar un usuario mediante su id")
    @PutMapping("/actualizar/{rut}")
    public ResponseEntity<UsuarioModelo> actualizar(@PathVariable String rut, @RequestBody UsuarioModelo usuario) {
        return usuarioService.findById(rut).map(u -> {
            usuario.setRutUsur(rut);
            UsuarioModelo actualizado = usuarioService.save(usuario);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "eliminar usuario por id", description = "este metodo se encarga de eliminar un usuario mediante su id")
    @DeleteMapping("/eliminar/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        if (usuarioService.findById(rut).isPresent()) {
            usuarioService.deleteById(rut);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "crear usuario completo", description = "este metodo se encarga de crear un usuario con incluyendole tambien una comuna y una ciudad")

    @PostMapping("/registrar-completo")
    public ResponseEntity<UsuarioModelo> registrarUsuarioCompleto(@RequestBody RegistroUsuarioCompletoDTO dto) {
        UsuarioModelo usuario = usuarioService.registrarUsuarioCompleto(dto);
        return ResponseEntity.ok(usuario);
    }
}