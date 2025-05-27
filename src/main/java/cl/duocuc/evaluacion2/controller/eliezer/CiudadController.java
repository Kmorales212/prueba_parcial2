package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    // Obtener todas las ciudades
    @GetMapping
    public ResponseEntity<List<CiudadModelo>> listarTodas() {
        List<CiudadModelo> ciudades = ciudadService.findAll();
        return ResponseEntity.ok(ciudades);
    }

    // Obtener una ciudad por ID
    @GetMapping("/{id}")
    public ResponseEntity<CiudadModelo> obtenerPorId(@PathVariable int id) {
        return ciudadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva ciudad
    @PostMapping
    public ResponseEntity<CiudadModelo> crear(@RequestBody CiudadModelo ciudad) {
        CiudadModelo creada = ciudadService.save(ciudad);
        return ResponseEntity.ok(creada);
    }

    // Actualizar una ciudad existente
    @PutMapping("/{id}")
    public ResponseEntity<CiudadModelo> actualizar(@PathVariable int id, @RequestBody CiudadModelo ciudad) {
        return ciudadService.findById(id).map(c -> {
            ciudad.setIdCiudad(id);
            CiudadModelo actualizada = ciudadService.save(ciudad);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una ciudad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (ciudadService.findById(id).isPresent()) {
            ciudadService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}