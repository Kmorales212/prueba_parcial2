package cl.duocuc.evaluacion2.controller.valeria;

import cl.duocuc.evaluacion2.dto.ProductoDTO;
import cl.duocuc.evaluacion2.model.ProductoModelo;
import cl.duocuc.evaluacion2.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
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


    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO dto) {
        ProductoModelo producto = productoService.crearProducto(toEntity(dto));
        return ResponseEntity.ok(toDTO(producto));
    }


    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        List<ProductoDTO> productos = productoService.obtenerTodos()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarPorId(id);
        return ResponseEntity.ok().build();
    }
}