package co.inventario.services.export;

import java.util.List;
import java.util.Map;

public class ReportData {
    private final String title;
    private final List<String> headers;
    private final List<List<Object>> rows;
    private final Map<String, Object> extraMetrics;

    public ReportData(String title, List<String> headers, List<List<Object>> rows) {
        this(title, headers, rows, null);
    }

    public ReportData(String title, List<String> headers, List<List<Object>> rows, Map<String, Object> extraMetrics) {
        this.title = title;
        this.headers = headers;
        this.rows = rows;
        this.extraMetrics = extraMetrics;
    }

    public String getTitle() { return title; }
    public List<String> getHeaders() { return headers; }
    public List<List<Object>> getRows() { return rows; }
    public Map<String, Object> getExtraMetrics() { return extraMetrics; }
}
