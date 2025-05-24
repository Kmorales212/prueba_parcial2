package cl.duocuc.evaluacion2.controller;

import cl.duocuc.evaluacion2.model.UsuarioModel;
import cl.duocuc.evaluacion2.service.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioServicio usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioModel> crear(@RequestBody UsuarioModel usuario) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listar() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

}
