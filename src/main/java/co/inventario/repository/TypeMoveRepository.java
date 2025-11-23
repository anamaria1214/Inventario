package co.inventario.repository;
import co.inventario.model.documents.TypeMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TypeMoveRepository extends JpaRepository<TypeMove, Long> {}