package co.inventario.model.documents;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "TypeMove")
public class TypeMove {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String move;

}
