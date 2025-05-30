package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.dto.RutaDTO;
import cl.duocuc.evaluacion2.model.EstadoRuta;
import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.service.RutaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @PostMapping("/crear")
    public ResponseEntity<RutaDTO> crearRuta(@RequestBody CrearRutaDTO dto) {
        RutaModel creada = rutaService.crearRuta(dto);

        RutaDTO response = new RutaDTO();
        response.setIdRuta(creada.getIdRuta());
        response.setFechaInicio(creada.getFechaInicio());
        response.setDescripcion(creada.getDescripcion());
        response.setEstado(creada.getEstado());
        response.setCiudadNombre(creada.getCiudad().getNombCiudad());

        response.setIdsEnvios(
                creada.getEnvios() != null ?
                        creada.getEnvios().stream().map(EnvioModelo::getIdEnvio).collect(Collectors.toList()) :
                        new ArrayList<>()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public List<RutaDTO> listarRutas() {
        return rutaService.getAllRutas().stream().map(ruta -> {
            RutaDTO dto = new RutaDTO();
            dto.setIdRuta(ruta.getIdRuta());
            dto.setFechaInicio(ruta.getFechaInicio());
            dto.setDescripcion(ruta.getDescripcion());
            dto.setEstado(ruta.getEstado());
            dto.setCiudadNombre(ruta.getCiudad().getNombCiudad());

            dto.setIdsEnvios(
                    ruta.getEnvios() != null ?
                            ruta.getEnvios().stream().map(EnvioModelo::getIdEnvio).collect(Collectors.toList()) :
                            new ArrayList<>()
            );
            return dto;
        }).collect(Collectors.toList());
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarRuta(@PathVariable("id") String id) {
        rutaService.eliminarRutaPorId(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/estado/{id}")
    public ResponseEntity<?> actualizarEstadoRuta(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            EstadoRuta nuevoEstado = EstadoRuta.valueOf(body.get("estado"));
            RutaModel rutaActualizada = rutaService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(rutaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
