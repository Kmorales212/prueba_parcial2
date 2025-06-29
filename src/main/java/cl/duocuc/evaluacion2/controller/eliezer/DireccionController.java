package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/direcciones")
@Tag(name = "Gestion de direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @Operation(summary = "listar todas las direcciones", description = "este metodo se encarga de listar todas las direcciones existentes en nuestra base de datos")
    @GetMapping("/listarTodas")
    public ResponseEntity<List<DireccionModelo>> listarTodas() {
        return ResponseEntity.ok(direccionService.findAll());
    }

    @Operation(summary = "buscar direccion por id", description = "este metodo se encarga de buscar una direccion mediante su id")
    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<DireccionModelo> obtenerPorId(@PathVariable int id) {
        return direccionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "crear direccion", description = "este metodo se encarga de crear una direccion")
    @PostMapping("/crear")
    public ResponseEntity<DireccionModelo> crear(@RequestBody DireccionModelo direccion) {
        DireccionModelo creada = direccionService.save(direccion);
        return ResponseEntity.ok(creada);
    }

    @Operation(summary = "actualizar direccion por id", description = "este metodo se encarga de actualizar una direccion mediante su id")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<DireccionModelo> actualizar(@PathVariable int id, @RequestBody DireccionModelo direccion) {
        return direccionService.findById(id).map(d -> {
            direccion.setIdDireccion(id);
            DireccionModelo actualizada = direccionService.save(direccion);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "eliminar direccion por id", description = "este metodo se encarga de eliminar una direccion mediante su id")
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