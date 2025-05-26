package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    /*@Autowired
    private RutaService rutaService;

    @GetMapping
    public List<RutaModel> getAllRutas() {
        return rutaService.getAllRutas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaModel> getRutaById(@PathVariable String id) {
        return rutaService.getRutaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RutaModel createRuta(@RequestBody RutaModel ruta) {
        return rutaService.createRuta(ruta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutaModel> updateRuta(@PathVariable String id, @RequestBody RutaModel rutaDetails) {
        return rutaService.updateRuta(id, rutaDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable String id) {
        boolean deleted = rutaService.deleteRuta(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

     */
}