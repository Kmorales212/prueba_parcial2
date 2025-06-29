package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.dto.RutaDTO;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.model.EstadoRuta;
import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import cl.duocuc.evaluacion2.repository.EnvioRepository;
import cl.duocuc.evaluacion2.repository.RutaRepository;
import cl.duocuc.evaluacion2.Assembler.RutaModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/rutas")
@Tag(name = "Gestión de Rutas V2")
public class RutaControllerV2 {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private RutaModelAssembler rutaModelAssembler;

    @Operation(summary = "Crear una nueva ruta V2")
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<RutaDTO>> crear(@RequestBody CrearRutaDTO dto) {
        RutaModel ruta = new RutaModel();
        ruta.setIdRuta(dto.getIdRuta());
        ruta.setFechaInicio(dto.getFechaInicio());
        ruta.setDescripcion(dto.getDescripcion());
        ruta.setEstado(EstadoRuta.PENDIENTE);

        CiudadModelo ciudad = ciudadRepository.findById(Integer.parseInt(dto.getCiudadId()))
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        ruta.setCiudad(ciudad);

        DireccionModelo direccionInicio = direccionRepository.findById(dto.getIdDireccionInicio())
                .orElseThrow(() -> new RuntimeException("Dirección de inicio no encontrada"));
        ruta.setDireccionInicio(direccionInicio);

        DireccionModelo direccionDestino = direccionRepository.findById(dto.getIdDireccionDestino())
                .orElseThrow(() -> new RuntimeException("Dirección de destino no encontrada"));
        ruta.setDireccionDestino(direccionDestino);

        List<EnvioModelo> envios = dto.getIdsEnvios().stream()
                .map(id -> envioRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Envío no encontrado: " + id)))
                .collect(Collectors.toList());
        ruta.setEnvios(envios);

        RutaModel creada = rutaRepository.save(ruta);
        return ResponseEntity.ok(rutaModelAssembler.toModel(toDTO(creada)));
    }

    @Operation(summary = "Listar todas las rutas V2")
    @GetMapping("/listarV2")
    public ResponseEntity<CollectionModel<EntityModel<RutaDTO>>> listarTodas() {
        List<EntityModel<RutaDTO>> rutas = rutaRepository.findAll().stream()
                .map(this::toDTO)
                .map(rutaModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(rutas,
                linkTo(methodOn(RutaControllerV2.class).listarTodas()).withSelfRel()));
    }

    @Operation(summary = "Obtener una ruta por ID V2")
    @GetMapping("/obtenerV2/{id}")
    public ResponseEntity<EntityModel<RutaDTO>> buscarPorId(@PathVariable String id) {
        return rutaRepository.findById(id)
                .map(this::toDTO)
                .map(rutaModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una ruta V2")
    @DeleteMapping("/eliminarV2/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (rutaRepository.existsById(id)) {
            rutaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar el estado de una ruta V2")
    @PutMapping("/estadoV2/{id}")
    public ResponseEntity<EntityModel<RutaDTO>> actualizarEstado(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            EstadoRuta nuevoEstado = EstadoRuta.valueOf(body.get("estado"));
            RutaModel ruta = rutaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
            ruta.setEstado(nuevoEstado);
            rutaRepository.save(ruta);
            return ResponseEntity.ok(rutaModelAssembler.toModel(toDTO(ruta)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private RutaDTO toDTO(RutaModel ruta) {
        RutaDTO dto = new RutaDTO();
        dto.setIdRuta(ruta.getIdRuta());
        dto.setFechaInicio(ruta.getFechaInicio());
        dto.setCiudadNombre(
                ruta.getCiudad() != null ? ruta.getCiudad().getNombCiudad() : "Sin ciudad"
        );
        dto.setEstado(ruta.getEstado());
        dto.setDescripcion(ruta.getDescripcion());
        dto.setIdsEnvios(
                ruta.getEnvios().stream().map(EnvioModelo::getIdEnvio).collect(Collectors.toList())
        );
        return dto;
    }
}
