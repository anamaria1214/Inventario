package co.inventario.dto.productDto;

public record UpdateProductDTO(
        String newName,
        String newDescription,
        double newSalePrice,
        double newBuyPrice,
        int newStockMinimo
) {
}
