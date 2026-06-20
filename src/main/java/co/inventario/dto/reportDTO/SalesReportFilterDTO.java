package co.inventario.dto.reportDTO;

import java.time.LocalDate;

public record SalesReportFilterDTO(LocalDate dateFrom, LocalDate dateTo, boolean includeMetrics, String format) {
}
