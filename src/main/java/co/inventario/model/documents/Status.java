package co.inventario.model.documents;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "status")
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
}
