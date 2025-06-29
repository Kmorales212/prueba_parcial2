package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.service.DireccionService;
import cl.duocuc.evaluacion2.Assembler.DireccionModelAssembler;
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
@RequestMapping("/api/direcciones")
@Tag(name = "Gestión de Direcciones V2", description = "Operaciones CRUD con HATEOAS")
public class DireccionControllerV2 {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private DireccionModelAssembler direccionModelAssembler;

    @Operation(
            summary = "Obtener todas las direcciones V2",
            description = "Retorna una lista de todas las direcciones con enlaces HATEOAS."
    )
    @GetMapping("/listarTodasV2")
    public ResponseEntity<CollectionModel<EntityModel<DireccionModelo>>> listarTodas() {
        List<DireccionModelo> direcciones = direccionService.findAll();

        List<EntityModel<DireccionModelo>> direccionResources = direcciones.stream()
                .map(direccionModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        direccionResources,
                        linkTo(methodOn(DireccionControllerV2.class).listarTodas()).withSelfRel()
                )
        );
    }

    @Operation(
            summary = "Obtener dirección por ID V2",
            description = "Retorna una dirección con enlaces HATEOAS."
    )
    @GetMapping("/obtenerPorIdV2/{id}")
    public ResponseEntity<EntityModel<DireccionModelo>> obtenerPorId(@PathVariable int id) {
        return direccionService.findById(id)
                .map(direccionModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva dirección V2",
            description = "Crea una dirección y retorna el recurso con enlaces HATEOAS."
    )
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<DireccionModelo>> crear(@RequestBody DireccionModelo direccion) {
        DireccionModelo creada = direccionService.save(direccion);
        EntityModel<DireccionModelo> resource = direccionModelAssembler.toModel(creada);
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Actualizar una dirección existente V2",
            description = "Actualiza una dirección y retorna el recurso con enlaces HATEOAS."
    )
    @PutMapping("/actualizarV2/{id}")
    public ResponseEntity<EntityModel<DireccionModelo>> actualizar(@PathVariable int id, @RequestBody DireccionModelo direccion) {
        return direccionService.findById(id).map(d -> {
            direccion.setIdDireccion(id);
            DireccionModelo actualizada = direccionService.save(direccion);
            EntityModel<DireccionModelo> resource = direccionModelAssembler.toModel(actualizada);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar una dirección V2",
            description = "Elimina una dirección por ID."
    )
    @DeleteMapping("/eliminarV2/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (direccionService.findById(id).isPresent()) {
            direccionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
