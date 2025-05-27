package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    // Obtener todas las direcciones
    @GetMapping("/listarTodas")
    public ResponseEntity<List<DireccionModelo>> listarTodas() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    // Obtener una dirección por ID
    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<DireccionModelo> obtenerPorId(@PathVariable int id) {
        return direccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva dirección
    @PostMapping("/crear")
    public ResponseEntity<DireccionModelo> crear(@RequestBody DireccionModelo direccion) {
        DireccionModelo creada = direccionService.save(direccion);
        return ResponseEntity.ok(creada);
    }

    // Actualizar una dirección existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<DireccionModelo> actualizar(@PathVariable int id, @RequestBody DireccionModelo direccion) {
        return direccionService.findById(id).map(d -> {
            direccion.setIdDireccion(id);
            DireccionModelo actualizada = direccionService.save(direccion);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una dirección
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (direccionService.findById(id).isPresent()) {
            direccionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}