package co.inventario.services.interfaces;

import co.inventario.dto.reportDTO.DatesRangeDTO;
import co.inventario.dto.reportDTO.ProductReportDTO;
import co.inventario.model.documents.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    Product articuloMasVendido();

    Double gananciasPorFecha(DatesRangeDTO datesRangeDTO);

    List<ProductReportDTO> productosVendidosPorFecha(DatesRangeDTO datesRangeDTO);

}
