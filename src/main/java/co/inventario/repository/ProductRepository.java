package co.inventario.repository;
import co.inventario.model.documents.Product;
import co.inventario.model.documents.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findByReference(String name);
    Product getById(long idProduct);
    List<Product> findByStatus(Status status);

}