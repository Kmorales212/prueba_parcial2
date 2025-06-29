package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.ComunaModelo;
import cl.duocuc.evaluacion2.service.ComunaService;
import cl.duocuc.evaluacion2.Assembler.ComunaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/comunas")
@Tag(name = "Gestión de Comunas V2", description = "Operaciones CRUD con HATEOAS")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ComunaModelAssembler comunaModelAssembler;

    @Operation(
            summary = "Obtener todas las comunas V2",
            description = "Retorna una lista de todas las comunas con enlaces HATEOAS."
    )
    @GetMapping("/listarTodasV2")
    public ResponseEntity<CollectionModel<EntityModel<ComunaModelo>>> listarTodas() {
        List<ComunaModelo> comunas = comunaService.findAll();

        List<EntityModel<ComunaModelo>> comunaResources = comunas.stream()
                .map(comunaModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        comunaResources,
                        linkTo(methodOn(ComunaControllerV2.class).listarTodas()).withSelfRel()
                )
        );
    }

    @Operation(
            summary = "Obtener comuna por ID V2",
            description = "Retorna una comuna con enlaces HATEOAS."
    )
    @GetMapping("/obtenerPorIdV2/{id}")
    public ResponseEntity<EntityModel<ComunaModelo>> obtenerPorId(@PathVariable int id) {
        return comunaService.findById(id)
                .map(comunaModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva comuna V2",
            description = "Crea una comuna y retorna el recurso con enlaces HATEOAS."
    )
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<ComunaModelo>> crear(@RequestBody ComunaModelo comuna) {
        ComunaModelo creada = comunaService.save(comuna);
        EntityModel<ComunaModelo> resource = comunaModelAssembler.toModel(creada);
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Actualizar una comuna existente V2",
            description = "Actualiza una comuna y retorna el recurso con enlaces HATEOAS."
    )
    @PutMapping("/actualizarV2/{id}")
    public ResponseEntity<EntityModel<ComunaModelo>> actualizar(@PathVariable int id, @RequestBody ComunaModelo comuna) {
        return comunaService.findById(id).map(c -> {
            comuna.setIdComuna(id);
            ComunaModelo actualizada = comunaService.save(comuna);
            EntityModel<ComunaModelo> resource = comunaModelAssembler.toModel(actualizada);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar una comuna V2",
            description = "Elimina una comuna por ID."
    )
    @DeleteMapping("/eliminarV2/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (comunaService.findById(id).isPresent()) {
            comunaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
