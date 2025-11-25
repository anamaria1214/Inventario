package co.inventario.repository;

import co.inventario.model.documents.Movement;

import co.inventario.model.documents.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByProduct(Product producto);
}