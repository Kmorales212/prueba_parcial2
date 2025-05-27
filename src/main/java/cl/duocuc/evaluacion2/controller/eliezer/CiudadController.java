package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping
    public ResponseEntity<List<CiudadModelo>> listarTodas() {
        return ResponseEntity.ok(ciudadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CiudadModelo> obtenerPorId(@PathVariable int id) {
        return ciudadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CiudadModelo> crear(@RequestBody CiudadModelo ciudad) {
        return ResponseEntity.ok(ciudadService.save(ciudad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CiudadModelo> actualizar(@PathVariable int id, @RequestBody CiudadModelo ciudad) {
        return ciudadService.findById(id).map(c -> {
            ciudad.setIdCiudad(id);
            return ResponseEntity.ok(ciudadService.save(ciudad));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        ciudadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}