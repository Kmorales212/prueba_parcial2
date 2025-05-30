package cl.duocuc.evaluacion2.repository;

import cl.duocuc.evaluacion2.model.ProductoModelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoModelo, Long> {
}