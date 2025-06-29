package cl.duocuc.evaluacion2.Assembler;

import cl.duocuc.evaluacion2.controller.eliezer.ComunaControllerV2;
import cl.duocuc.evaluacion2.model.ComunaModelo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<ComunaModelo, EntityModel<ComunaModelo>> {

    @Override
    public EntityModel<ComunaModelo> toModel(ComunaModelo comuna) {
        return EntityModel.of(comuna,
                linkTo(methodOn(ComunaControllerV2.class).obtenerPorId(comuna.getIdComuna())).withSelfRel(),
                linkTo(methodOn(ComunaControllerV2.class).listarTodas()).withRel("all"),
                linkTo(methodOn(ComunaControllerV2.class).actualizar(comuna.getIdComuna(), comuna)).withRel("update"),
                linkTo(methodOn(ComunaControllerV2.class).eliminar(comuna.getIdComuna())).withRel("delete")
        );
    }
}
