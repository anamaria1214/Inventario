package co.inventario.controller;

import co.inventario.dto.globalDto.MensajeDTO;
import co.inventario.dto.productDto.NewProductDTO;
import co.inventario.dto.productDto.UpdateProductDTO;
import co.inventario.exceptions.ProductException;
import co.inventario.exceptions.StatusException;
import co.inventario.model.documents.Product;
import co.inventario.services.interfaces.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "localhost",  allowCredentials = "true")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<MensajeDTO<Product>> createProduct(@RequestBody NewProductDTO newProductDTO){
        Product pro= productService.newProduct(newProductDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false,pro));

    }

    @PutMapping("/update")
    public ResponseEntity<MensajeDTO<Product>> updateProduct(@RequestBody UpdateProductDTO updateProductDTO) throws ProductException{
        Product pro=productService.updateProduct(updateProductDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false,pro));
    }

    @DeleteMapping("/delete/{idProduct}")
    public ResponseEntity<MensajeDTO<Product>> deleteProduct(@PathVariable long idProduct) throws ProductException{
        Product pro=productService.deleteProduct(idProduct);
        return ResponseEntity.ok(new MensajeDTO<>(false,pro));
    }

    @GetMapping("/getAll")
    public ResponseEntity<MensajeDTO<List<Product>>> getAllProducts(){
        List<Product> pros= productService.getAllProducts();
        return ResponseEntity.ok(new MensajeDTO<>(false,pros));
    }

    @GetMapping("/getByStatus/{statusName}")
    public ResponseEntity<MensajeDTO<List<Product>>> getProductsByStatus(@PathVariable String statusName) throws StatusException{
        List<Product> pros=productService.getProductsByStatus(statusName);
        return ResponseEntity.ok(new MensajeDTO<>(false,pros));
    }

}
