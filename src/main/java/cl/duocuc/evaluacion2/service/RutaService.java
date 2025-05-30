package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.dto.CrearRutaDTO;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import cl.duocuc.evaluacion2.model.EnvioModelo;
import cl.duocuc.evaluacion2.model.EstadoRuta;
import cl.duocuc.evaluacion2.model.RutaModel;
import cl.duocuc.evaluacion2.repository.CiudadRepository;
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

    public RutaModel crearRuta(CrearRutaDTO dto) {
        RutaModel ruta = new RutaModel();

        ruta.setIdRuta(dto.getIdRuta());
        ruta.setFechaInicio(dto.getFechaInicio());
        ruta.setDescripcion(dto.getDescripcion());
        ruta.setDireccionInicio(dto.getDireccionInicio());
        ruta.setDireccionDestino(dto.getDireccionDestino());
        ruta.setEstado(EstadoRuta.PENDIENTE);


        CiudadModelo ciudad = ciudadRepository.findById(Integer.parseInt(dto.getCiudadId()))
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        ruta.setCiudad(ciudad);

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
