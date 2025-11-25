package co.inventario.services.interfaces;

import co.inventario.dto.productDto.NewProductDTO;
import co.inventario.dto.productDto.UpdateProductDTO;
import co.inventario.exceptions.ProductException;
import co.inventario.exceptions.StatusException;
import co.inventario.model.documents.Product;

import java.util.List;

public interface ProductService {

    Product newProduct(NewProductDTO newProductDTO) throws ProductException;

    Product updateProduct(UpdateProductDTO updateProductDTO) throws ProductException;

    Product deleteProduct(long idProduct) throws ProductException;

    List<Product> getAllProducts();

    List<Product> getProductsByStatus(String statusName) throws StatusException;

    Product getProducById(long idProduct) throws ProductException;
}
