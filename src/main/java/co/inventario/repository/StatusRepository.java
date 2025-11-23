package co.inventario.repository;

import co.inventario.model.documents.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {}
