package co.inventario.services.implementations;

import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.exceptions.MovementException;
import co.inventario.model.documents.Movement;
import co.inventario.model.documents.Product;
import co.inventario.model.documents.TypeMove;
import co.inventario.repository.MovementRepository;
import co.inventario.repository.ProductRepository;
import co.inventario.repository.TypeMoveRepository;
import co.inventario.services.interfaces.MovementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor

public class MovementeServiceImplementation implements MovementService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final TypeMoveRepository typeMoveRepository;

    /**
     * Creates a new movement for a given product by updating its stock and saving the movement in the repository.
     * The movement type can be either "IN" (to add stock) or "OUT" (to remove stock).
     *
     * @param newMovementDTO the data transfer object containing the product ID and the amount for the movement,
     *                       the type of the movement, either "IN" or "OUT"
     * @return the saved Movement entity after processing the request
     * @throws MovementException if the product does not exist, the amount is invalid, the movement type is invalid,
     *                           or there is insufficient stock for an "OUT" movement
     */
    @Override
    public Movement newMovement(NewMovementDTO newMovementDTO) throws MovementException {
        // Validación del producto
        Product product = productRepository.findById(newMovementDTO.productId())
                .orElseThrow(() -> new MovementException("El producto no existe. ID=" + newMovementDTO.productId()));

        // Validar cantidad
        if (newMovementDTO.amount() <= 0) {
            throw new MovementException("La cantidad debe ser mayor a 0.");
        }

        // Obtener el tipo de movimiento (IN / OUT)
        TypeMove typeMove = typeMoveRepository.findByMove(newMovementDTO.moveType().toUpperCase())
                .orElseThrow(() -> new MovementException("Tipo de movimiento inválido: " + newMovementDTO.moveType()));

        // Actualizar stock según IN / OUT
        if (newMovementDTO.moveType().equalsIgnoreCase("IN")) {
            product.setStock(product.getStock() + (int) newMovementDTO.amount());
        } else if (newMovementDTO.moveType().equalsIgnoreCase("OUT")) {
            if (product.getStock() < newMovementDTO.amount()) {
                throw new MovementException("Stock insuficiente para realizar una salida.");
            }
            product.setStock(product.getStock() - (int) newMovementDTO.amount());
        }

        productRepository.save(product); //Actualiza la información del producto

        // Crear el movimiento
        Movement movement = Movement.builder()
                .product(product)
                .typeMove(typeMove)
                .amount(newMovementDTO.amount())
                .date_move(LocalDateTime.now())
                .build();

        return movementRepository.save(movement);
    }
}
