package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.service.CiudadService;
import cl.duocuc.evaluacion2.Assembler.CiudadModelAssembler;
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
@RequestMapping("/api/ciudades")
@Tag(name = "Gestión de Ciudades V2", description = "metodos con hateoas")
public class CiudadControllerV2 {

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private CiudadModelAssembler ciudadModelAssembler;

    @Operation(
            summary = "Obtener todas las ciudades V2",
            description = "devuelve una lista de todas las ciudades con enlaces hateoas"
    )
    @GetMapping("/listarTodasV2")
    public ResponseEntity<CollectionModel<EntityModel<CiudadModelo>>> listarTodas() {
        List<CiudadModelo> ciudades = ciudadService.findAll();

        List<EntityModel<CiudadModelo>> ciudadResources = ciudades.stream()
                .map(ciudadModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        ciudadResources,
                        linkTo(methodOn(CiudadControllerV2.class).listarTodas()).withSelfRel()
                )
        );
    }

    @Operation(
            summary = "Obtener ciudad por ID V2",
            description = "devuelve una ciudad con enlaces hateoas"
    )
    @GetMapping("/obtenerPorIdV2/{id}")
    public ResponseEntity<EntityModel<CiudadModelo>> obtenerPorId(@PathVariable int id) {
        return ciudadService.findById(id)
                .map(ciudadModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva ciudad V2",
            description = "Crea una ciudad y devuelve la entidad con enlaces hateoas"
    )
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<CiudadModelo>> crear(@RequestBody CiudadModelo ciudad) {
        CiudadModelo creada = ciudadService.save(ciudad);
        EntityModel<CiudadModelo> resource = ciudadModelAssembler.toModel(creada);
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Actualizar una ciudad existente V2",
            description = "Actualiza una ciudad y devuelve la entidad con enlaces hateoas"
    )
    @PutMapping("/actualizarV2/{id}")
    public ResponseEntity<EntityModel<CiudadModelo>> actualizar(@PathVariable int id, @RequestBody CiudadModelo ciudad) {
        return ciudadService.findById(id).map(c -> {
            ciudad.setIdCiudad(id);
            CiudadModelo actualizada = ciudadService.save(ciudad);
            EntityModel<CiudadModelo> resource = ciudadModelAssembler.toModel(actualizada);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar una ciudad V2",
            description = "Elimina una ciudad por ID"
    )
    @DeleteMapping("/eliminarV2/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (ciudadService.findById(id).isPresent()) {
            ciudadService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
