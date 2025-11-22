package co.inventario.repository;

import co.inventario.model.documents.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long>{
}
