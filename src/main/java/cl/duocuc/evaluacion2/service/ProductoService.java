package cl.duocuc.evaluacion2.service;

import cl.duocuc.evaluacion2.model.ProductoModelo;
import cl.duocuc.evaluacion2.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoModelo crearProducto(ProductoModelo producto) {
        return productoRepository.save(producto);
    }

    public List<ProductoModelo> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<ProductoModelo> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        productoRepository.deleteById(id);
    }
}