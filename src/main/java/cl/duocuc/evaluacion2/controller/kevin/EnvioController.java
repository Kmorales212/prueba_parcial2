package cl.duocuc.evaluacion2.controller.kevin;



import cl.duocuc.evaluacion2.dto.CrearEnvioDTO;
import cl.duocuc.evaluacion2.dto.EnvioDTO;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.model.EstadoEnvio;
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
@Tag(name = "Gestion de Envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Operation(summary = "crear envio nuevo", description = "este metodo se encarga de crear un nuevo envio y se guarda en la base de datos")
    @PostMapping("/crear")
    public ResponseEntity<EnvioDTO> crearEnvio(@RequestBody CrearEnvioDTO dto) {
        EnvioModelo model = new EnvioModelo();
        model.setIdEnvio(dto.getIdEnvio());
        model.setFechaEnvio(dto.getFechaEnvio());
        model.setDireccionEntrega(dto.getDireccionEntrega());
        model.setEstado(dto.getEstado());


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

    @Operation(summary = "listar todos los envios", description = "este metodo se encarga de listar todos los envios existentes en nuestra base de datos")
    @GetMapping("/listarTodo")
    public List<EnvioDTO> listarEnvios() {
        return envioService.getAllEnvios().stream().map(envio -> {
            EnvioDTO dto = new EnvioDTO();
            dto.setIdEnvio(envio.getIdEnvio());
            dto.setFechaEnvio(envio.getFechaEnvio());
            dto.setEstado(envio.getEstado());
            dto.setDireccionEntregaResumen(
                    envio.getDireccionEntrega().getNombDireccion()
            );
            return dto;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "buscar un envio por su id", description = "este metodo se encarga de buscar un envio en nuestra base de datos mediante su id")
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
    @Operation(summary = "eliminar envio por su id", description = "este metodo se encarga de eliminar un envio guardado en la base de datos mediante su id")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable String id) {
        Optional<EnvioModelo> envioOpt = envioService.getEnvioById(id);

        if (envioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        envioService.deleteEnvio(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "actualiza el estado del envio por id", description = "este metodo se encarga de actualizar el estado del envio por ejemplo de preparando a entregando mediante a su id")
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
