package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.service.ComunaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/comunas")
@Tag(name = "Gestion de comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @Operation(summary = "listar todas las comunas", description = "este metodo se encarga de listar todas las comunas existentes en nuestra base de datos")
    @GetMapping("/listarTodas")
    public ResponseEntity<List<ComunaModelo>> listarTodas() {
        return ResponseEntity.ok(comunaService.findAll());
    }

    @Operation(summary = "buscar comuna por id", description = "este metodo se encarga de buscar una comuna mediante su id")
    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<ComunaModelo> obtenerPorId(@PathVariable int id) {
        return comunaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "crear comunas", description = "este metodo se encarga de crear una comuna")
    @PostMapping("/crear")
    public ResponseEntity<ComunaModelo> crear(@RequestBody ComunaModelo comuna) {
        ComunaModelo creada = comunaService.save(comuna);
        return ResponseEntity.ok(creada);
    }

    @Operation(summary = "actualizar comuna por id", description = "este metodo se encarga de actualizar una comuna mediante su id")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ComunaModelo> actualizar(@PathVariable int id, @RequestBody ComunaModelo comuna) {
        return comunaService.findById(id).map(c -> {
            comuna.setIdComuna(id);
            ComunaModelo actualizada = comunaService.save(comuna);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "eliminar comuna por id", description = "este metodo se encarga de eliminar una comuna mediante su id")
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