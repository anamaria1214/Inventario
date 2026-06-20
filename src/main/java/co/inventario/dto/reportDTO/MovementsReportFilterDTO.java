package co.inventario.dto.reportDTO;

import java.time.LocalDate;

public record MovementsReportFilterDTO(LocalDate dateFrom, LocalDate dateTo, String movementType, String format) {
}
