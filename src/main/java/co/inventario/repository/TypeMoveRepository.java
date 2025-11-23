package co.inventario.repository;

import co.inventario.model.documents.TypeMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TypeMoveRepository extends JpaRepository<TypeMove, Long> {
    Optional<TypeMove> findByMove(String move);

}
