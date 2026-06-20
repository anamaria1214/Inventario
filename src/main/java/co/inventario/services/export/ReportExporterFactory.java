package co.inventario.services.export;

import co.inventario.services.interfaces.MovementService;

public class ReportExporterFactory {

    public static MovementService.ReportExporter getExporter(String format) {
        if ("excel".equalsIgnoreCase(format)) {
            return new ExcelReportExporter();
        } else if ("pdf".equalsIgnoreCase(format)) {
            return new PdfReportExporter();
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }
}
