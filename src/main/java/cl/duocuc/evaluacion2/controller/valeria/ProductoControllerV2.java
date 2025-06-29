package cl.duocuc.evaluacion2.controller.valeria;

import cl.duocuc.evaluacion2.dto.ProductoDTO;
import cl.duocuc.evaluacion2.model.ProductoModelo;
import cl.duocuc.evaluacion2.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Gestión de Productos V2")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Operation(
            summary = "Listar todos los productos V2",
            description = "Retorna todos los productos con enlaces HATEOAS."
    )
    @GetMapping("/listarV2")
    public ResponseEntity<CollectionModel<EntityModel<ProductoDTO>>> listarTodos() {
        List<EntityModel<ProductoDTO>> productos = productoService.obtenerTodos()
                .stream()
                .map(this::toDTO)
                .map(this::toEntityModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        productos,
                        linkTo(methodOn(ProductoControllerV2.class).listarTodos()).withSelfRel()
                )
        );
    }

    @Operation(
            summary = "Obtener producto por ID V2",
            description = "Retorna un producto con enlaces HATEOAS."
    )
    @GetMapping("/buscarV2/{id}")
    public ResponseEntity<EntityModel<ProductoDTO>> buscarPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(this::toDTO)
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo producto V2",
            description = "Crea un producto y retorna el recurso con enlaces HATEOAS."
    )
    @PostMapping("/crearV2")
    public ResponseEntity<EntityModel<ProductoDTO>> crear(@RequestBody ProductoDTO dto) {
        ProductoModelo creado = productoService.crearProducto(toEntity(dto));
        return ResponseEntity.ok(toEntityModel(toDTO(creado)));
    }

    @Operation(
            summary = "Actualizar un producto V2",
            description = "Actualiza un producto existente y retorna el recurso con enlaces HATEOAS."
    )
    @PutMapping("/actualizarV2/{id}")
    public ResponseEntity<EntityModel<ProductoDTO>> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return productoService.obtenerPorId(id).map(p -> {
            p.setNombre(dto.getNombre());
            p.setDescripcion(dto.getDescripcion());
            p.setPrecio(dto.getPrecio());
            ProductoModelo actualizado = productoService.crearProducto(p);
            return ResponseEntity.ok(toEntityModel(toDTO(actualizado)));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar un producto V2",
            description = "Elimina un producto por ID."
    )
    @DeleteMapping("/eliminarV2/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    private ProductoDTO toDTO(ProductoModelo producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    private ProductoModelo toEntity(ProductoDTO dto) {
        ProductoModelo producto = new ProductoModelo();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        return producto;
    }

    private EntityModel<ProductoDTO> toEntityModel(ProductoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProductoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).listarTodos()).withRel("all"),
                linkTo(methodOn(ProductoControllerV2.class).eliminar(dto.getId())).withRel("delete")
        );
    }
}
