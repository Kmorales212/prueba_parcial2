package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.service.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    // Obtener todas las comunas
    @GetMapping("/listarTodas")
    public ResponseEntity<List<ComunaModelo>> listarTodas() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    // Obtener una comuna por ID
    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<ComunaModelo> obtenerPorId(@PathVariable int id) {
        return comunaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva comuna
    @PostMapping("/crear")
    public ResponseEntity<ComunaModelo> crear(@RequestBody ComunaModelo comuna) {
        ComunaModelo creada = comunaService.save(comuna);
        return ResponseEntity.ok(creada);
    }

    // Actualizar una comuna existente
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ComunaModelo> actualizar(@PathVariable int id, @RequestBody ComunaModelo comuna) {
        return comunaService.findById(id).map(c -> {
            comuna.setIdComuna(id);
            ComunaModelo actualizada = comunaService.save(comuna);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una comuna
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (comunaService.findById(id).isPresent()) {
            comunaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}