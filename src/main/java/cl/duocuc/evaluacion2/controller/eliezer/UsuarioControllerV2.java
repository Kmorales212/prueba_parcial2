package cl.duocuc.evaluacion2.controller.eliezer;

import cl.duocuc.evaluacion2.dto.RegistroUsuarioCompletoDTO;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import cl.duocuc.evaluacion2.service.UsuarioService;
import cl.duocuc.evaluacion2.Assembler.UsuarioModelAssembler;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Gestión de Usuarios V2", description = "Operaciones CRUD con HATEOAS")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Operation(
            summary = "Obtener todos los usuarios V2",
            description = "Retorna una lista de todos los usuarios con enlaces HATEOAS."
    )
    @GetMapping("/listarTodasV2")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioModelo>>> listarTodas() {
        List<UsuarioModelo> usuarios = usuarioService.findAll();

        List<EntityModel<UsuarioModelo>> usuarioResources = usuarios.stream()
                .map(usuarioModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        usuarioResources,
                        linkTo(methodOn(UsuarioControllerV2.class).listarTodas()).withSelfRel()
                )
        );
    }

    @Operation(
            summary = "Obtener usuario por RUT V2",
            description = "Retorna un usuario con enlaces HATEOAS."
    )
    @GetMapping("/obtenerPorIdV2/{rut}")
    public ResponseEntity<EntityModel<UsuarioModelo>> obtenerPorId(@PathVariable String rut) {
        return usuarioService.findById(rut)
                .map(usuarioModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo usuario V2",
            description = "Crea un usuario y retorna el recurso con enlaces HATEOAS."
    )
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<UsuarioModelo>> crear(@RequestBody UsuarioModelo usuario) {
        UsuarioModelo creado = usuarioService.save(usuario);
        EntityModel<UsuarioModelo> resource = usuarioModelAssembler.toModel(creado);
        return ResponseEntity.ok(resource);
    }

    @Operation(
            summary = "Actualizar un usuario existente V2",
            description = "Actualiza un usuario y retorna el recurso con enlaces HATEOAS."
    )
    @PutMapping("/actualizarV2/{rut}")
    public ResponseEntity<EntityModel<UsuarioModelo>> actualizar(@PathVariable String rut, @RequestBody UsuarioModelo usuario) {
        return usuarioService.findById(rut).map(u -> {
            usuario.setRutUsur(rut);
            UsuarioModelo actualizado = usuarioService.save(usuario);
            EntityModel<UsuarioModelo> resource = usuarioModelAssembler.toModel(actualizado);
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar un usuario V2",
            description = "Elimina un usuario por RUT."
    )
    @DeleteMapping("/eliminarV2/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        if (usuarioService.findById(rut).isPresent()) {
            usuarioService.deleteById(rut);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Crear usuario completo V2",
            description = "Este método permite crear un usuario incluyendo la dirección, la comuna y la ciudad en una sola petición."
    )
    @PostMapping("/registrar-completoV2")
    public ResponseEntity<UsuarioModelo> registrarUsuarioCompleto(@RequestBody RegistroUsuarioCompletoDTO dto) {
        UsuarioModelo usuario = usuarioService.registrarUsuarioCompleto(dto);
        return ResponseEntity.ok(usuario);
    }

}
