package co.inventario.dto.moveDto;

//El usuario solo manda el id del producto y la cantidad del mismo, desde el servicio se específica el tipo de movimiento.
public record NewMovementDTO(
        String moveType,
        Long productId,
        double amount
) {}