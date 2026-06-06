package co.inventario.dto.reportDTO;

import java.time.LocalDateTime;

public record ProductReportDTO(LocalDateTime buyDate, String name, Double amount, Double precioUnidad, Double precioTotal) {
}
