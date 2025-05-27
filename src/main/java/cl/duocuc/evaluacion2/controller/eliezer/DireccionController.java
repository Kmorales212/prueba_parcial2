package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<DireccionModelo>> listarTodas() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionModelo> obtenerPorId(@PathVariable int id) {
        return direccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DireccionModelo> crear(@RequestBody DireccionModelo direccion) {
        return ResponseEntity.ok(direccionService.save(direccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionModelo> actualizar(@PathVariable int id, @RequestBody DireccionModelo direccion) {
        return direccionService.findById(id).map(d -> {
            direccion.setIdDireccion(id);
            return ResponseEntity.ok(direccionService.save(direccion));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        direccionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}