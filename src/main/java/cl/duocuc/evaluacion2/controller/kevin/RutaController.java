package cl.duocuc.evaluacion2.controller.kevin;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.dto.RutaDTO;
import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.service.RutaService;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.EnvioModelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @PostMapping
    public ResponseEntity<RutaDTO> crearRuta(@RequestBody CrearRutaDTO dto) {
        RutaModel model = new RutaModel();
        model.setIdRuta(dto.getIdRuta());
        model.setFechaInicio(dto.getFechaInicio());
        model.setDescripcion(dto.getDescripcion());

        // Ciudad desde ID (convertimos String a Integer)
        CiudadModelo ciudad = new CiudadModelo();
        ciudad.setIdCiudad(Integer.parseInt(dto.getCiudadId()));
        model.setCiudad(ciudad);

        // Direcciones
        model.setDireccionInicio(dto.getDireccionInicio());
        model.setDireccionDestino(dto.getDireccionDestino());

        // Envíos asociados (si los tienes como ID, deberías mapearlos a EnvioModelo con solo el ID)
        List<EnvioModelo> envios = new ArrayList<>();
        if (dto.getIdsEnvios() != null) {
            for (String idEnvio : dto.getIdsEnvios()) {
                EnvioModelo envio = new EnvioModelo();
                envio.setIdEnvio(idEnvio);
                envio.setRuta(model); // Estableces la relación inversa
                envios.add(envio);
            }
        }
        model.setEnvios(envios);

        RutaModel creada = rutaService.createRuta(model);

        // Convertimos a DTO de respuesta
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

    @GetMapping
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
}
