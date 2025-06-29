package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.model.*;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
import cl.duocuc.evaluacion2.repository.DireccionRepository;
import cl.duocuc.evaluacion2.repository.EnvioRepository;
import cl.duocuc.evaluacion2.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private DireccionRepository direccionRepository;


    public RutaModel crearRuta(CrearRutaDTO dto) {
        RutaModel ruta = new RutaModel();

        ruta.setIdRuta(dto.getIdRuta());
        ruta.setFechaInicio(dto.getFechaInicio());
        ruta.setDescripcion(dto.getDescripcion());
        ruta.setEstado(EstadoRuta.PENDIENTE);

        CiudadModelo ciudad = ciudadRepository.findById(Integer.parseInt(dto.getCiudadId()))
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        ruta.setCiudad(ciudad);

        // Cargar Direccion de inicio
        DireccionModelo direccionInicio = direccionRepository.findById(dto.getIdDireccionInicio())
                .orElseThrow(() -> new RuntimeException("Dirección de inicio no encontrada"));
        ruta.setDireccionInicio(direccionInicio);

        // Cargar Direccion de destino
        DireccionModelo direccionDestino = direccionRepository.findById(dto.getIdDireccionDestino())
                .orElseThrow(() -> new RuntimeException("Dirección de destino no encontrada"));
        ruta.setDireccionDestino(direccionDestino);

        // Cargar envíos
        List<EnvioModelo> envios = dto.getIdsEnvios().stream()
                .map(id -> envioRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Envío no encontrado: " + id)))
                .collect(Collectors.toList());
        ruta.setEnvios(envios);

        return rutaRepository.save(ruta);
    }

    public List<RutaModel> getAllRutas() {
        return rutaRepository.findAll();
    }
    public void eliminarRutaPorId(String idRuta) {
        rutaRepository.deleteById(idRuta);
    }
    public RutaModel actualizarEstado(String id, EstadoRuta nuevoEstado) {
        RutaModel ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));

        ruta.setEstado(nuevoEstado);
        return rutaRepository.save(ruta);
    }


}
