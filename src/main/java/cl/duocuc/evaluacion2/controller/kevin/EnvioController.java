package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.dto.CrearEnvioDTO;
import cl.duocuc.evaluacion2.dto.EnvioDTO;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.model.EstadoEnvio;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import cl.duocuc.evaluacion2.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envios")
@Tag(name = "Gestión de Envíos")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private DireccionRepository direccionRepository;

    @Operation(summary = "Crear envío nuevo", description = "Este método se encarga de crear un nuevo envío y guardarlo en la base de datos")
    @PostMapping("/crear")
    public ResponseEntity<EnvioDTO> crearEnvio(@RequestBody CrearEnvioDTO dto) {
        EnvioModelo model = new EnvioModelo();
        model.setIdEnvio(dto.getIdEnvio());
        model.setFechaEnvio(dto.getFechaEnvio());
        model.setEstado(dto.getEstado());

        if (dto.getIdDireccionEntrega() != null) {
            DireccionModelo direccion = direccionRepository.findById(dto.getIdDireccionEntrega())
                    .orElseThrow(() -> new RuntimeException(
                            "Dirección no encontrada con ID: " + dto.getIdDireccionEntrega()
                    ));
            model.setDireccionEntrega(direccion);
        }

        EnvioModelo creado = envioService.createEnvio(model);

        EnvioDTO response = new EnvioDTO();
        response.setIdEnvio(creado.getIdEnvio());
        response.setFechaEnvio(creado.getFechaEnvio());
        response.setEstado(creado.getEstado());

        String resumen = "";
        if (creado.getDireccionEntrega() != null &&
                creado.getDireccionEntrega().getComuna() != null &&
                creado.getDireccionEntrega().getComuna().getCiudad() != null) {
            resumen = creado.getDireccionEntrega().getNombDireccion() + " " +
                    creado.getDireccionEntrega().getNumDireccion() + ", " +
                    creado.getDireccionEntrega().getComuna().getCiudad().getNombCiudad();
        } else {
            resumen = "Dirección incompleta";
        }
        response.setDireccionEntregaResumen(resumen);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todos los envíos", description = "Este método se encarga de listar todos los envíos existentes en la base de datos")
    @GetMapping("/listarTodo")
    public List<EnvioDTO> listarEnvios() {
        return envioService.getAllEnvios().stream().map(envio -> {
            EnvioDTO dto = new EnvioDTO();
            dto.setIdEnvio(envio.getIdEnvio());
            dto.setFechaEnvio(envio.getFechaEnvio());
            dto.setEstado(envio.getEstado());

            if (envio.getDireccionEntrega() != null) {
                dto.setDireccionEntregaResumen(envio.getDireccionEntrega().getNombDireccion());
            } else {
                dto.setDireccionEntregaResumen("Dirección no disponible");
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "Buscar un envío por su ID", description = "Este método se encarga de buscar un envío en la base de datos mediante su ID")
    @GetMapping("/listar/{id}")
    public ResponseEntity<EnvioDTO> obtenerEnvio(@PathVariable String id) {
        Optional<EnvioModelo> envioOpt = envioService.getEnvioById(id);

        if (envioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EnvioModelo envio = envioOpt.get();

        EnvioDTO dto = new EnvioDTO();
        dto.setIdEnvio(envio.getIdEnvio());
        dto.setFechaEnvio(envio.getFechaEnvio());
        dto.setEstado(envio.getEstado());

        if (envio.getDireccionEntrega() != null) {
            dto.setDireccionEntregaResumen(envio.getDireccionEntrega().getNombDireccion());
        } else {
            dto.setDireccionEntregaResumen("Dirección no disponible");
        }

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Eliminar envío por su ID", description = "Este método se encarga de eliminar un envío guardado en la base de datos mediante su ID")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable String id) {
        Optional<EnvioModelo> envioOpt = envioService.getEnvioById(id);

        if (envioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        envioService.deleteEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar el estado del envío por ID", description = "Este método actualiza el estado del envío, por ejemplo, de PREPARANDO a ENTREGANDO, mediante su ID")
    @PutMapping("/estado/{id}")
    public ResponseEntity<?> actualizarEstadoEnvio(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            EstadoEnvio nuevoEstado = EstadoEnvio.valueOf(body.get("estado"));
            return envioService.actualizarEstado(id, nuevoEstado)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido");
        }
    }
}