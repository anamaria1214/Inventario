package co.inventario.dto.reportDTO;

public record ProductsReportFilterDTO(boolean includeInactive, String sortBy, String format) {
}
