package co.inventario.model.documents;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime created_at;
    private int stock;
    private String reference;
    private double sale_price;
    private double buy_price;
    private int stock_minimo;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
}
