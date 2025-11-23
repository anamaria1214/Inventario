package co.inventario.services.implementations;

import co.inventario.dto.productDto.NewProductDTO;
import co.inventario.dto.productDto.UpdateProductDTO;
import co.inventario.exceptions.ProductException;
import co.inventario.exceptions.StatusException;
import co.inventario.model.documents.Product;
import co.inventario.model.documents.Status;
import co.inventario.repository.ProductRepository;
import co.inventario.repository.StatusRepository;
import co.inventario.services.interfaces.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final StatusRepository statusRepository;

    /**
     * Creates a new product based on the provided NewProductDTO object.
     * Validates input parameters, ensures the uniqueness of the product reference,
     * and assigns a default status before saving the product in the repository.
     *
     * @param newProductDTO the data transfer object containing details for the new product;
     *                      must include name, reference, sale price, buy price,
     *                      stock, and stock minimum.
     * @return the newly created Product entity with all details populated and saved.
     * @throws ProductException if the product name is null or blank, or if a product
     *                          with the same reference already exists.
     */
    @Override
    public Product newProduct(NewProductDTO newProductDTO) throws ProductException {
        // Validación básica
        if (newProductDTO.name() == null || newProductDTO.name().isBlank()) {
            throw new ProductException("El nombre del producto es obligatorio.");
        }

        // Verificar referencia duplicada
        Product productCheck = getProductByReference(newProductDTO.reference());
        if (productCheck != null) {
            throw new ProductException("Ya existe un producto con la referencia: " + newProductDTO.reference());
        }

        // Estado por defecto
        Status statusActive = getStatus("ACTIVO");

        // Construir producto
        Product newProduct = Product.builder()
                .name(newProductDTO.name())
                .description(newProductDTO.description())
                .reference(newProductDTO.reference())
                .sale_price(newProductDTO.salePrice())
                .buy_price(newProductDTO.buyPrice())
                .stock_minimo(newProductDTO.stockMinimo())
                .stock(newProductDTO.stock())
                .created_at(LocalDateTime.now())
                .status(statusActive)
                .build();

        return productRepository.save(newProduct);
    }

    /**
     * Updates an existing product with new details provided in the UpdateProductDTO object.
     * Retrieves the product by its ID, updates its attributes, and persists the changes
     * to the repository.
     *
     * @param updateProductDTO  the data transfer object containing the new product details,
     *                          including name, description, sale price, buy price, and
     *                          minimum stock
     * @return the updated Product entity after applying the changes
     * @throws ProductException if the product with the specified ID is not found
     */
    @Override
    public Product updateProduct(UpdateProductDTO updateProductDTO) throws ProductException {
        Product updateProduct= getProductById(updateProductDTO.idProduct());
        updateProduct.setName(updateProductDTO.newName());
        updateProduct.setDescription(updateProductDTO.newDescription());
        updateProduct.setSale_price(updateProductDTO.newSalePrice());
        updateProduct.setBuy_price(updateProductDTO.newBuyPrice());
        updateProduct.setStock_minimo(updateProductDTO.newStockMinimo());

        return productRepository.save(updateProduct);

    }

    /**
     * Marks a product as inactive by updating its status to "INACTIVO".
     * Retrieves the product by its ID, updates the status, and persists the changes to the repository.
     *
     * @param idProduct the unique identifier of the product to be marked as inactive
     * @return the updated Product entity after the status has been changed to "INACTIVO"
     * @throws ProductException if the product with the specified ID is not found
     */
    @Override
    public Product deleteProduct(long idProduct) throws ProductException {
        Status statusInactivo = getStatus("INACTIVO");
        Product deleteProduct= getProductById(idProduct);
        deleteProduct.setStatus(statusInactivo);
        return productRepository.save(deleteProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByStatus(String statusName) throws StatusException {
        Status status = getStatus(statusName);
        return productRepository.findByStatus(status);
    }

    //----------------Private methods---------------------
    private Status getStatus(String status) throws StatusException {
        return statusRepository.findByStatus(status).orElseThrow(() -> new StatusException("No se encontro el estado: " + status));
    }

    private Product getProductByName(String name){
        return productRepository.findByName(name);
    }
    private Product getProductByReference(String reference){
        return productRepository.findByReference(reference);
    }
    private Product getProductById(long idProduct) throws ProductException{
        return productRepository.findById(idProduct).orElseThrow(() -> new ProductException("Producto no encontrado"));
    }



}
