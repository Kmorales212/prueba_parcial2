package cl.duocuc.evaluacion2.Assembler;


import cl.duocuc.evaluacion2.controller.eliezer.UsuarioControllerV2;
import cl.duocuc.evaluacion2.model.UsuarioModelo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioModelo, EntityModel<UsuarioModelo>> {

    @Override
    public EntityModel<UsuarioModelo> toModel(UsuarioModelo usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).obtenerPorId(usuario.getRutUsur())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarTodas()).withRel("all"),
                linkTo(methodOn(UsuarioControllerV2.class).actualizar(usuario.getRutUsur(), usuario)).withRel("update"),
                linkTo(methodOn(UsuarioControllerV2.class).eliminar(usuario.getRutUsur())).withRel("delete")
        );
    }
}
