package cl.duocuc.evaluacion2.Assembler;

import cl.duocuc.evaluacion2.controller.valeria.ProductoControllerV2;
import cl.duocuc.evaluacion2.dto.ProductoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {

    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProductoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).listarTodos()).withRel("all"),
                linkTo(methodOn(ProductoControllerV2.class).actualizar(dto.getId(), dto)).withRel("update"),
                linkTo(methodOn(ProductoControllerV2.class).eliminar(dto.getId())).withRel("delete")
        );
    }
}
