package cl.duocuc.evaluacion2.Assembler;

import cl.duocuc.evaluacion2.controller.eliezer.DireccionControllerV2;
import cl.duocuc.evaluacion2.model.DireccionModelo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DireccionModelAssembler implements RepresentationModelAssembler<DireccionModelo, EntityModel<DireccionModelo>> {

    @Override
    public EntityModel<DireccionModelo> toModel(DireccionModelo direccion) {
        return EntityModel.of(direccion,
                linkTo(methodOn(DireccionControllerV2.class).obtenerPorId(direccion.getIdDireccion())).withSelfRel(),
                linkTo(methodOn(DireccionControllerV2.class).listarTodas()).withRel("all"),
                linkTo(methodOn(DireccionControllerV2.class).actualizar(direccion.getIdDireccion(), direccion)).withRel("update"),
                linkTo(methodOn(DireccionControllerV2.class).eliminar(direccion.getIdDireccion())).withRel("delete")
        );
    }
}
