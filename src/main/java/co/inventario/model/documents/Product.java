package co.inventario.model.documents;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    private String name;
    private String description;
    private LocalDateTime created_at;
    private int stock;
    private String reference;
    private double sale_price;
    private double buy_price;
    private int stock_minimo;
    private Long status;



}
