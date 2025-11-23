package co.inventario.model.documents;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "type_move")
@Data
public class TypeMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String move;
}
