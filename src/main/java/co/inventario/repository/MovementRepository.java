package co.inventario.repository;

import co.inventario.model.documents.Movement;

import co.inventario.model.documents.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    @Query
    Status findByStatus(String status);

}