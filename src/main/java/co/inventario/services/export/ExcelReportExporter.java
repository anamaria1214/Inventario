package co.inventario.services.export;

import co.inventario.services.interfaces.MovementService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelReportExporter implements MovementService.ReportExporter {

    @Override
    public byte[] export(ReportData data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte");

            int rowNum = 0;

            // Title
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(data.getTitle());
            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleCell.setCellStyle(titleStyle);

            rowNum++; // Spacer

            // Extra metrics (if present)
            if (data.getExtraMetrics() != null && !data.getExtraMetrics().isEmpty()) {
                Row metricsHeaderRow = sheet.createRow(rowNum++);
                Cell metricsHeaderCell = metricsHeaderRow.createCell(0);
                metricsHeaderCell.setCellValue("Resumen / Métricas:");
                CellStyle boldStyle = workbook.createCellStyle();
                Font boldFont = workbook.createFont();
                boldFont.setBold(true);
                boldStyle.setFont(boldFont);
                metricsHeaderCell.setCellStyle(boldStyle);

                for (Map.Entry<String, Object> entry : data.getExtraMetrics().entrySet()) {
                    Row metricRow = sheet.createRow(rowNum++);
                    metricRow.createCell(0).setCellValue(entry.getKey() + ":");
                    metricRow.createCell(1).setCellValue(entry.getValue().toString());
                }
                rowNum++; // Spacer
            }

            // Headers
            Row headerRow = sheet.createRow(rowNum++);
            List<String> headers = data.getHeaders();
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            List<List<Object>> rows = data.getRows();
            if (rows != null) {
                for (List<Object> rowData : rows) {
                    Row row = sheet.createRow(rowNum++);
                    for (int i = 0; i < rowData.size(); i++) {
                        Object value = rowData.get(i);
                        if (value instanceof Number) {
                            row.createCell(i).setCellValue(((Number) value).doubleValue());
                        } else {
                            row.createCell(i).setCellValue(value != null ? value.toString() : "");
                        }
                    }
                }
            }

            // Auto-size columns
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
