package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.dto.CrearEnvioDTO;
import cl.duocuc.evaluacion2.dto.EnvioDTO;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.model.EstadoEnvio;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import cl.duocuc.evaluacion2.service.EnvioService;
import cl.duocuc.evaluacion2.Assembler.EnvioModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Gestión de Envíos V2", description = "Operaciones CRUD de envíos con HATEOAS y DTO")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private EnvioModelAssembler envioModelAssembler;

    @Operation(
            summary = "Listar todos los envíos V2",
            description = "Retorna todos los envíos con enlaces HATEOAS."
    )
    @GetMapping("/listarV2")
    public ResponseEntity<CollectionModel<EntityModel<EnvioDTO>>> listarTodos() {
        List<EntityModel<EnvioDTO>> envios = envioService.getAllEnvios()
                .stream()
                .map(this::toDTO)
                .map(envioModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        envios,
                        linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withSelfRel()
                )
        );

    }

    @Operation(
            summary = "Obtener un envío por ID V2",
            description = "Retorna un envío con enlaces HATEOAS."
    )
    @GetMapping("/buscarV2/{id}")
    public ResponseEntity<EntityModel<EnvioDTO>> buscarPorId(@PathVariable String id) {
        return envioService.getEnvioById(id)
                .map(this::toDTO)
                .map(envioModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo envío V2",
            description = "Crea un envío con una dirección y retorna el recurso con enlaces HATEOAS."
    )
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<EnvioDTO>> crear(@RequestBody CrearEnvioDTO dto) {
        EnvioModelo creado = envioService.createEnvio(toEntity(dto));
        return ResponseEntity.ok(
                envioModelAssembler.toModel(toDTO(creado))
        );
    }

    @Operation(
            summary = "Eliminar un envío V2",
            description = "Elimina un envío por ID."
    )
    @DeleteMapping("/eliminarV2/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        boolean eliminado = envioService.deleteEnvio(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Actualizar el estado de un envío V2",
            description = "Actualiza el estado del envío por ID y retorna el recurso con enlaces HATEOAS."
    )
    @PutMapping("/estadoV2/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            EstadoEnvio nuevoEstado = EstadoEnvio.valueOf(body.get("estado"));

            Optional<EnvioModelo> actualizado = envioService.actualizarEstado(id, nuevoEstado);

            if (actualizado.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            EnvioDTO dto = toDTO(actualizado.get());
            return ResponseEntity.ok(envioModelAssembler.toModel(dto));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido");
        }
    }




    private EnvioDTO toDTO(EnvioModelo envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setFechaEnvio(envio.getFechaEnvio());
        dto.setEstado(envio.getEstado());
        if (envio.getDireccionEntrega() != null) {
            dto.setDireccionEntregaResumen(
                    envio.getDireccionEntrega().getNombDireccion() + " #" + envio.getDireccionEntrega().getNumDireccion()
            );
        } else {
            dto.setDireccionEntregaResumen("Sin dirección");
        }
        return dto;
    }

    private EnvioModelo toEntity(CrearEnvioDTO dto) {
        EnvioModelo envio = new EnvioModelo();
        envio.setIdEnvio(dto.getIdEnvio());
        envio.setFechaEnvio(dto.getFechaEnvio());
        envio.setEstado(dto.getEstado());

        if (dto.getIdDireccionEntrega() != null) {
            DireccionModelo direccion = direccionRepository.findById(dto.getIdDireccionEntrega())
                    .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + dto.getIdDireccionEntrega()));
            envio.setDireccionEntrega(direccion);
        }

        return envio;
    }
}
