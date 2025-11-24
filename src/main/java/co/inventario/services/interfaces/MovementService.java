package co.inventario.services.interfaces;

import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.exceptions.MovementException;
import co.inventario.model.documents.Movement;

import java.util.List;

public interface MovementService {

    Movement newMovement(NewMovementDTO newMovementDTO) throws MovementException;

    List<Movement> getAllMovements();
}
