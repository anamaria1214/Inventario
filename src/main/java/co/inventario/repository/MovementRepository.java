package co.inventario.repository;

import co.inventario.model.documents.Movement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {}