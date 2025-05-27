package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.service.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaModelo>> listarTodas() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaModelo> obtenerPorId(@PathVariable int id) {
        return comunaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ComunaModelo> crear(@RequestBody ComunaModelo comuna) {
        return ResponseEntity.ok(comunaService.save(comuna));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        comunaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<ComunaModelo> actualizar(@PathVariable int id, @RequestBody ComunaModelo comuna) {
        return comunaService.findById(id).map(c -> {
            comuna.setIdComuna(id);
            return ResponseEntity.ok(comunaService.save(comuna));
        }).orElse(ResponseEntity.notFound().build());
    }
}