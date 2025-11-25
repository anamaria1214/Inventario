package co.inventario.services.implementations;

import co.inventario.dto.reportDTO.DatesRangeDTO;
import co.inventario.dto.reportDTO.ProductReportDTO;
import co.inventario.model.documents.Movement;
import co.inventario.model.documents.Product;
import co.inventario.repository.MovementRepository;
import co.inventario.repository.ProductRepository;
import co.inventario.services.interfaces.ReportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            int ventas = ventasPorProducto(p);
            productosVentas.put(p, ventas);
        }

        return productosVentas.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public Double gananciasPorFecha(DatesRangeDTO datesRangeDTO) {
        double ganancias= 0.0;
        List<Movement> movimientos= movementRepository.findAll();
        for(int i=0;i<movimientos.size();i++){
            if(movimientos.get(i).getDate_move().isBefore(datesRangeDTO.next())
                    && movimientos.get(i).getDate_move().isAfter(datesRangeDTO.prev())
                    && movimientos.get(i).getTypeMove().getMove().equals("OUT"))
            {
                ganancias+=movimientos.get(i).getAmount()*movimientos.get(i).getProduct().getSale_price();
            }
        }
        return ganancias;
    }

    @Override
    public List<ProductReportDTO> productosVendidosPorFecha(DatesRangeDTO datesRangeDTO) {
        List<ProductReportDTO> productosVendidos= new ArrayList<>();
        List<Movement> movimientos= movementRepository.findAll();

        for(int i=0;i<movimientos.size();i++){
            if(movimientos.get(i).getDate_move().isBefore(datesRangeDTO.next())
                    && movimientos.get(i).getDate_move().isAfter(datesRangeDTO.prev()) && movimientos.get(i).getTypeMove().getMove().equals("OUT")){

                Product product =movimientos.get(i).getProduct();
                productosVendidos.add(
                        new ProductReportDTO(movimientos.get(i).getDate_move(), product.getName(),
                                movimientos.get(i).getAmount(), product.getSale_price(),
                                product.getSale_price()*movimientos.get(i).getAmount()));
            }

        }
        return productosVendidos;
    }

    // Métodos privados

    private int ventasPorProducto(Product producto){
        List<Movement> movimientos= movementRepository.findByProduct(producto);
        int amount= 0;
        for (int i =0; i<movimientos.size(); i++){
            amount+=movimientos.get(i).getAmount();
        }
        return amount;
    }
}
