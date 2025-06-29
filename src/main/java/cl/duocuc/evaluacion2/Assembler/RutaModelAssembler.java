package cl.duocuc.evaluacion2.Assembler;

import cl.duocuc.evaluacion2.controller.kevin.RutaControllerV2;
import cl.duocuc.evaluacion2.dto.RutaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RutaModelAssembler implements RepresentationModelAssembler<RutaDTO, EntityModel<RutaDTO>> {

    @Override
    public EntityModel<RutaDTO> toModel(RutaDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(RutaControllerV2.class).buscarPorId(dto.getIdRuta())).withSelfRel(),
                linkTo(methodOn(RutaControllerV2.class).listarTodas()).withRel("all"),
                linkTo(methodOn(RutaControllerV2.class).eliminar(dto.getIdRuta())).withRel("delete")
        );
    }
}
