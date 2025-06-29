package cl.duocuc.evaluacion2.Assembler;

import cl.duocuc.evaluacion2.controller.kevin.EnvioControllerV2;
import cl.duocuc.evaluacion2.dto.EnvioDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<EnvioDTO, EntityModel<EnvioDTO>> {

    @Override
    public EntityModel<EnvioDTO> toModel(EnvioDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(EnvioControllerV2.class).buscarPorId(dto.getIdEnvio())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withRel("all"),
                linkTo(methodOn(EnvioControllerV2.class).eliminar(dto.getIdEnvio())).withRel("delete")
        );
    }
}
