package co.inventario.controller;

import co.inventario.dto.globalDto.MensajeDTO;
import co.inventario.dto.reportDTO.*;
import co.inventario.model.documents.Product;
import co.inventario.services.interfaces.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/products")
    public ResponseEntity<byte[]> productsReport(
            @RequestParam(defaultValue = "false") boolean includeInactive,
            @RequestParam String sortBy,
            @RequestParam String format) {
        byte[] file = reportService.generarReporteProductos(includeInactive, sortBy, format);
        return buildFileResponse(file, format, "reporte-productos");
    }

    @GetMapping("/movements")
    public ResponseEntity<byte[]> movementsReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "all") String movementType,
            @RequestParam String format) {
        byte[] file = reportService.generarReporteMovimientos(dateFrom, dateTo, movementType, format);
        return buildFileResponse(file, format, "reporte-movimientos");
    }

    @GetMapping("/sales")
    public ResponseEntity<byte[]> salesReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "false") boolean includeMetrics,
            @RequestParam String format) {
        byte[] file = reportService.generarReporteVentas(dateFrom, dateTo, includeMetrics, format);
        return buildFileResponse(file, format, "reporte-ventas");
    }

    @GetMapping("/low-stock")
    public ResponseEntity<byte[]> lowStockReport(
            @RequestParam(defaultValue = "default") String threshold,
            @RequestParam String format) {
        byte[] file = reportService.generarReporteStockBajo(threshold, format);
        return buildFileResponse(file, format, "reporte-stock-bajo");
    }

    // --- Helper ---

    private ResponseEntity<byte[]> buildFileResponse(byte[] file, String format, String baseName) {
        boolean isPdf = "pdf".equalsIgnoreCase(format);
        MediaType mediaType = isPdf
                ? MediaType.APPLICATION_PDF
                : MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String extension = isPdf ? "pdf" : "xlsx";

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + baseName + "." + extension + "\"")
                .body(file);
    }
}
