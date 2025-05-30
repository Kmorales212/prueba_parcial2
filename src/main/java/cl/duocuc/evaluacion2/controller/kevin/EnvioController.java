package cl.duocuc.evaluacion2.controller.kevin;



import cl.duocuc.evaluacion2.dto.CrearEnvioDTO;
import cl.duocuc.evaluacion2.dto.EnvioDTO;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.model.EstadoEnvio;
import cl.duocuc.evaluacion2.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;


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

    @GetMapping("/lstarTodo")
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
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable String id) {
        Optional<EnvioModelo> envioOpt = envioService.getEnvioById(id);

        if (envioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        envioService.deleteEnvio(id);
        return ResponseEntity.noContent().build();
    }
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
