package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.dto.RutaDTO;
import cl.duocuc.evaluacion2.model.EstadoRuta;
import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.service.RutaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rutas")
@Tag(name = "Gestion de Rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @Operation(summary = "crear ruta nueva", description = "este metodo se encarga de crear una nueva ruta y se guarda en la base de datos")
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

    @Operation(summary = "listar todas las rutas", description = "este metodo se encarga de listar todas las rutas existentes en nuestra base de datos")
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

    @Operation(summary = "eliminar ruta por su id", description = "este metodo se encarga de eliminar una ruta guardada en la base de datos mediante su id")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarRuta(@PathVariable("id") String id) {
        rutaService.eliminarRutaPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "actualiza el estado de la ruta por id", description = "este metodo se encarga de actualizar el estado de la ruta por ejemplo de en camino a entregando mediante a su id")
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
