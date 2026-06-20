package co.inventario.services.export;

import co.inventario.services.interfaces.MovementService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PdfReportExporter implements MovementService.ReportExporter {

    @Override
    public byte[] export(ReportData data) throws IOException {
        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
            Font boldBodyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.DARK_GRAY);
            Font infoFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11, Color.GRAY);

            // Title
            Paragraph title = new Paragraph(data.getTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Date generated
            String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            Paragraph datePara = new Paragraph("Generado el: " + dateStr, dateFont);
            datePara.setAlignment(Element.ALIGN_CENTER);
            datePara.setSpacingAfter(20);
            document.add(datePara);

            // Extra metrics (if present)
            if (data.getExtraMetrics() != null && !data.getExtraMetrics().isEmpty()) {
                Paragraph metricsTitle = new Paragraph("Resumen / Métricas:", boldBodyFont);
                metricsTitle.setSpacingAfter(5);
                document.add(metricsTitle);

                for (Map.Entry<String, Object> entry : data.getExtraMetrics().entrySet()) {
                    Paragraph metric = new Paragraph(entry.getKey() + ": " + entry.getValue(), bodyFont);
                    metric.setIndentationLeft(20);
                    metric.setSpacingAfter(3);
                    document.add(metric);
                }
                Paragraph spacing = new Paragraph(" ");
                spacing.setSpacingAfter(10);
                document.add(spacing);
            }

            // Table
            List<String> headers = data.getHeaders();
            List<List<Object>> rows = data.getRows();

            if (rows == null || rows.isEmpty()) {
                Paragraph noData = new Paragraph("Sin resultados para los filtros seleccionados.", infoFont);
                noData.setAlignment(Element.ALIGN_CENTER);
                noData.setSpacingBefore(20);
                document.add(noData);
            } else {
                PdfPTable table = new PdfPTable(headers.size());
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                // Add Headers
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                    cell.setBackgroundColor(new Color(41, 128, 185)); // Beautiful Blue
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPadding(6);
                    table.addCell(cell);
                }

                // Add Rows
                boolean isAlt = false;
                for (List<Object> row : rows) {
                    for (Object val : row) {
                        String text = val == null ? "" : val.toString();
                        PdfPCell cell = new PdfPCell(new Phrase(text, bodyFont));
                        cell.setPadding(5);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        if (isAlt) {
                            cell.setBackgroundColor(new Color(245, 247, 250)); // Light Zebra
                        }
                        table.addCell(cell);
                    }
                    isAlt = !isAlt;
                }

                document.add(table);
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF document", e);
        }

        return out.toByteArray();
    }
}
