package co.inventario.dto.productDto;

import lombok.Getter;

public record NewProductDTO(
        String name ,
        String description,
        int stock,
        String reference,
        double salePrice,
        double buyPrice,
        int stockMinimo
) {}