package co.inventario.controller;

import co.inventario.dto.globalDto.MensajeDTO;
import co.inventario.dto.reportDTO.DatesRangeDTO;
import co.inventario.dto.reportDTO.ProductReportDTO;
import co.inventario.model.documents.Product;
import co.inventario.services.interfaces.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;


    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/gains")
    public ResponseEntity<MensajeDTO<Double>> totalGainsPerDate(DatesRangeDTO datesRangeDTO){
        double gains= reportService.gananciasPorFecha(datesRangeDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, gains));
    }

    @GetMapping("/sales")
    public ResponseEntity<MensajeDTO<List<ProductReportDTO>>> productsSold (DatesRangeDTO datesRangeDTO){
        List<ProductReportDTO> report= reportService.productosVendidosPorFecha(datesRangeDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, report));
    }

    @GetMapping("bestselling")
    public ResponseEntity<MensajeDTO<Product>> bestSellingProduct(){
        Product product= reportService.articuloMasVendido();
        return ResponseEntity.ok(new MensajeDTO<>(false, product));
    }
}
