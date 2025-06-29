package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.service.CiudadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//EliezerCarrasco
@RestController
@RequestMapping("/api/ciudades")
@Tag(name = "Gestion de Ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @Operation(summary = "listar todas las ciudades", description = "este metodo se encarga de mostrar la lista de todas las ciudades existentes en nuestra base de datos")
    @GetMapping("/listarTodas")
    public ResponseEntity<List<CiudadModelo>> listarTodas() {
        List<CiudadModelo> ciudades = ciudadService.findAll();
        return ResponseEntity.ok(ciudades);
    }

    @Operation(summary = "buscar ciudadad por id", description = "este metodo se encarga de buscar una ciudad existente mediante su id")
    @GetMapping("/obtenerPorId/{id}")
    public ResponseEntity<CiudadModelo> obtenerPorId(@PathVariable int id) {
        return ciudadService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "crear ciudad", description = "este metodo se encarga de crear una ciudad")
    @PostMapping("/crear")
    public ResponseEntity<CiudadModelo> crear(@RequestBody CiudadModelo ciudad) {
        CiudadModelo creada = ciudadService.save(ciudad);
        return ResponseEntity.ok(creada);
    }

    @Operation(summary = "actualizar ciudad por id", description = "este metodo se encarga de actualizar una ciudad existente mediante su id")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CiudadModelo> actualizar(@PathVariable int id, @RequestBody CiudadModelo ciudad) {
        return ciudadService.findById(id).map(c -> {
            ciudad.setIdCiudad(id);
            CiudadModelo actualizada = ciudadService.save(ciudad);
            return ResponseEntity.ok(actualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "eliminar ciudad por id", description = "este metodo se encarga de eliminar una ciudad existente mediante su id")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (ciudadService.findById(id).isPresent()) {
            ciudadService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}