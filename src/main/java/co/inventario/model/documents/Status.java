package co.inventario.model.documents;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
}
