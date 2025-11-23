package co.inventario.services.implementations;

import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.exceptions.MovementException;
import co.inventario.model.documents.Movement;
import co.inventario.repository.MovementRepository;
import co.inventario.services.interfaces.MovementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor

public class MovementeServiceImplementation implements MovementService {

    private final MovementRepository movementRepository;

    @Override
    public Movement newMovement(NewMovementDTO newMovementDTO) throws MovementException {
        return null;
    }
}
