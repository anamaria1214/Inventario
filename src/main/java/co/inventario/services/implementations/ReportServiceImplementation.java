package co.inventario.services.implementations;

import co.inventario.dto.reportDTO.DatesRangeDTO;
import co.inventario.dto.reportDTO.ProductReportDTO;
import co.inventario.model.documents.Movement;
import co.inventario.model.documents.Product;
import co.inventario.repository.MovementRepository;
import co.inventario.repository.ProductRepository;
import co.inventario.services.export.ReportData;
import co.inventario.services.export.ReportExporterFactory;
import co.inventario.services.interfaces.ReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImplementation implements ReportService {

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;

    @Override
    public Product articuloMasVendido() {
        List<Product> productos = productRepository.findAll();
        Map<Product, Integer> productosVentas = new HashMap<>();
        for (Product p : productos) {
            productosVentas.put(p, ventasPorProducto(p));
        }
        return productosVentas.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public Double gananciasPorFecha(DatesRangeDTO datesRangeDTO) {
        return getMovimientosEnRango(datesRangeDTO).stream()
                .filter(m -> m.getTypeMove().getMove().equals("OUT"))
                .mapToDouble(m -> m.getAmount() * m.getProduct().getSale_price())
                .sum();
    }

    @Override
    public List<ProductReportDTO> productosVendidosPorFecha(DatesRangeDTO datesRangeDTO) {
        return getMovimientosEnRango(datesRangeDTO).stream()
                .filter(m -> m.getTypeMove().getMove().equals("OUT"))
                .map(m -> new ProductReportDTO(m.getDate_move(), m.getProduct().getName(),
                        m.getAmount(), m.getProduct().getSale_price(),
                        m.getProduct().getSale_price() * m.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] generarReporteProductos(boolean includeInactive, String sortBy, String format) {
        List<Product> productos = productRepository.findAll();
        if (!includeInactive) {
            productos = productos.stream()
                    .filter(p -> p.getStatus() != null && "ACTIVO".equalsIgnoreCase(p.getStatus().getStatus()))
                    .collect(Collectors.toList());
        }
        // Simplified sorting
        productos.sort(Comparator.comparing(Product::getName));
        
        List<List<Object>> rows = productos.stream()
                .map(p -> Arrays.asList((Object)p.getName(), p.getStock(), p.getSale_price()))
                .collect(Collectors.toList());
        
        ReportData data = new ReportData("Reporte de Productos", Arrays.asList("Nombre", "Stock", "Precio"), rows);
        return exportar(data, format);
    }

    @Override
    public byte[] generarReporteMovimientos(LocalDate dateFrom, LocalDate dateTo, String movementType, String format) {
        List<Movement> movimientos = movementRepository.findAll().stream()
                .filter(m -> m.getDate_move().toLocalDate().isAfter(dateFrom.minusDays(1)) && m.getDate_move().toLocalDate().isBefore(dateTo.plusDays(1)))
                .filter(m -> "all".equalsIgnoreCase(movementType) || m.getTypeMove().getMove().equalsIgnoreCase(movementType))
                .collect(Collectors.toList());

        List<List<Object>> rows = movimientos.stream()
                .map(m -> Arrays.asList((Object)m.getDate_move().toLocalDate(), m.getProduct().getName(), m.getTypeMove().getMove(), m.getAmount()))
                .collect(Collectors.toList());
        
        ReportData data = new ReportData("Reporte de Movimientos", Arrays.asList("Fecha", "Producto", "Tipo", "Cantidad"), rows);
        return exportar(data, format);
    }

    @Override
    public byte[] generarReporteVentas(LocalDate dateFrom, LocalDate dateTo, boolean includeMetrics, String format) {
        DatesRangeDTO range = new DatesRangeDTO(dateFrom.atStartOfDay(), dateTo.plusDays(1).atStartOfDay());
        List<ProductReportDTO> ventas = productosVendidosPorFecha(range);

        List<List<Object>> rows = ventas.stream()
                .map(v -> Arrays.asList((Object)v.buyDate().toLocalDate(), v.name(), v.amount(), v.precioTotal()))
                .collect(Collectors.toList());
        
        Map<String, Object> metrics = new HashMap<>();
        if (includeMetrics) {
            metrics.put("Ganancias Totales", gananciasPorFecha(range));
        }

        ReportData data = new ReportData("Reporte de Ventas", Arrays.asList("Fecha", "Producto", "Cantidad", "Total"), rows, metrics);
        return exportar(data, format);
    }

    @Override
    public byte[] generarReporteStockBajo(String threshold, String format) {
        int limit = "default".equalsIgnoreCase(threshold) ? 5 : Integer.parseInt(threshold);
        List<Product> productos = productRepository.findAll().stream()
                .filter(p -> p.getStock() < limit)
                .collect(Collectors.toList());
        
        List<List<Object>> rows = productos.stream()
                .map(p -> Arrays.asList((Object)p.getName(), p.getStock(), p.getStock_minimo()))
                .collect(Collectors.toList());
        
        ReportData data = new ReportData("Reporte de Stock Bajo", Arrays.asList("Producto", "Stock Actual", "Stock Mínimo"), rows);
        return exportar(data, format);
    }

    // Privados

    private byte[] exportar(ReportData data, String format) {
        try {
            return ReportExporterFactory.getExporter(format).export(data);
        } catch (IOException e) {
            throw new RuntimeException("Error exportando reporte", e);
        }
    }

    private List<Movement> getMovimientosEnRango(DatesRangeDTO range) {
        return movementRepository.findAll().stream()
                .filter(m -> m.getDate_move().isAfter(range.prev()) && m.getDate_move().isBefore(range.next()))
                .collect(Collectors.toList());
    }

    private int ventasPorProducto(Product producto){
        return movementRepository.findByProduct(producto).stream().mapToInt(m -> (int)m.getAmount()).sum();
    }
}
