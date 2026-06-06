package co.inventario.dto.reportDTO;

import java.time.LocalDateTime;

public record DatesRangeDTO(LocalDateTime prev, LocalDateTime next) {
}
