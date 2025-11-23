package co.inventario.services.interfaces;

import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.exceptions.MovementException;
import co.inventario.model.documents.Movement;

public interface MovementService {

    Movement newMovement(NewMovementDTO newMovementDTO, String moveType) throws MovementException;

}
