package cl.duocuc.evaluacion2.Assembler;


import cl.duocuc.evaluacion2.controller.eliezer.CiudadControllerV2;
import cl.duocuc.evaluacion2.model.CiudadModelo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CiudadModelAssembler implements RepresentationModelAssembler<CiudadModelo, EntityModel<CiudadModelo>> {

    @Override
    public EntityModel<CiudadModelo> toModel(CiudadModelo ciudad) {
        return EntityModel.of(ciudad,
                linkTo(methodOn(CiudadControllerV2.class).obtenerPorId(ciudad.getIdCiudad())).withSelfRel(),
                linkTo(methodOn(CiudadControllerV2.class).listarTodas()).withRel("all"),
                linkTo(methodOn(CiudadControllerV2.class).actualizar(ciudad.getIdCiudad(), ciudad)).withRel("update"),
                linkTo(methodOn(CiudadControllerV2.class).eliminar(ciudad.getIdCiudad())).withRel("delete")
        );
    }
}
