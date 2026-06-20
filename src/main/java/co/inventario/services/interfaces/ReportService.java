package co.inventario.services.interfaces;

import co.inventario.dto.reportDTO.DatesRangeDTO;
import co.inventario.dto.reportDTO.ProductReportDTO;
import co.inventario.model.documents.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    Product articuloMasVendido();

    Double gananciasPorFecha(DatesRangeDTO datesRangeDTO);

    List<ProductReportDTO> productosVendidosPorFecha(DatesRangeDTO datesRangeDTO);

    byte[] generarReporteProductos(boolean includeInactive, String sortBy, String format);

    byte[] generarReporteMovimientos(LocalDate dateFrom, LocalDate dateTo, String movementType, String format);

    byte[] generarReporteVentas(LocalDate dateFrom, LocalDate dateTo, boolean includeMetrics, String format);

    byte[] generarReporteStockBajo(String threshold, String format);
}
