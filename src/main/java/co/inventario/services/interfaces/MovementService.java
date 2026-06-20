package co.inventario.services.interfaces;

import co.inventario.dto.moveDto.NewMovementDTO;
import co.inventario.exceptions.MovementException;
import co.inventario.model.documents.Movement;
import co.inventario.services.export.ReportData;

import java.io.IOException;
import java.util.List;

public interface MovementService {

    Movement newMovement(NewMovementDTO newMovementDTO) throws MovementException;

    List<Movement> getAllMovements();

    interface ReportExporter {
        byte[] export(ReportData data) throws IOException;
    }
}
