package cl.duocuc.evaluacion2.controller.kevin;



import cl.duocuc.evaluacion2.dto.CrearEnvioDTO;
import cl.duocuc.evaluacion2.dto.EnvioDTO;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;


    @PostMapping
    public ResponseEntity<EnvioDTO> crearEnvio(@RequestBody CrearEnvioDTO dto) {
        EnvioModelo model = new EnvioModelo();
        model.setIdEnvio(dto.getIdEnvio());
        model.setFechaEnvio(dto.getFechaEnvio());
        model.setDireccionEntrega(dto.getDireccionEntrega());

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

    @GetMapping
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

    @GetMapping("/{id}")
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

}
