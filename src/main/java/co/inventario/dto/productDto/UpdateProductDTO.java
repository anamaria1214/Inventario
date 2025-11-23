package co.inventario.dto.productDto;

public record UpdateProductDTO(
        long idProduct,
        String newName,
        String newDescription,
        double newSalePrice,
        double newBuyPrice,
        int newStockMinimo
) {
}
