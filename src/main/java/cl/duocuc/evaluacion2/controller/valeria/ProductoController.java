package cl.duocuc.evaluacion2.controller.valeria;

import cl.duocuc.evaluacion2.dto.ProductoDTO;
import cl.duocuc.evaluacion2.model.ProductoModelo;
import cl.duocuc.evaluacion2.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Gestion de Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


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

    @Operation(summary = "crear producto", description = "este metodo crea un producto nuevo")
    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO dto) {
        ProductoModelo producto = productoService.crearProducto(toEntity(dto));
        return ResponseEntity.ok(toDTO(producto));
    }

    @Operation(summary = "listar todos los productos", description = "este metodo se encarga de listar todos los productos existentes en nuestra base de datos")
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.obtenerTodos()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }


    @Operation(summary = "buscar por id", description = "este metodo se encarga de buscar los datos de un producto mediante su id")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "actualizar por id", description = "este metodo se encarga de buscar un producto por su id para luego poder actualizar algun dato de el mismo")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        return productoService.obtenerPorId(id).map(productoExistente -> {
            productoExistente.setNombre(dto.getNombre());
            productoExistente.setDescripcion(dto.getDescripcion());
            productoExistente.setPrecio(dto.getPrecio());

            ProductoModelo actualizado = productoService.crearProducto(productoExistente);
            return ResponseEntity.ok(toDTO(actualizado));
        }).orElse(ResponseEntity.notFound().build());
    }



    @Operation(summary = "eliminar por id", description = "este metodo se encarga de eliminar un producto mediante su id")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarPorId(id);
        return ResponseEntity.ok().build();
    }
}