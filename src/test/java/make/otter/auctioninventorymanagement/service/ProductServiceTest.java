package make.otter.auctioninventorymanagement.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import make.otter.auctioninventorymanagement.domain.dto.ProductResponse;
import make.otter.auctioninventorymanagement.domain.entity.ProductStock;
import make.otter.auctioninventorymanagement.repository.ProductStockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
@Transactional
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    ProductStockRepository productStockRepository;

    @Autowired
    EntityManager em;

    @Test
    void createProduct() {
        ProductResponse productResponse = productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));

        ProductResponse findProduct = productService.read(productResponse.getProductId());
        ProductStock productStock = productStockRepository.findProductStockByProducts_ProductId(productResponse.getProductId()).orElseThrow();

        Assertions.assertNotNull(findProduct);
        Assertions.assertNotNull(productStock);

        Assertions.assertEquals(productResponse.getProductId(), findProduct.getProductId());
        Assertions.assertEquals(1, productStock.getStock());
    }

    @Test
    void updateProduct(){
        ProductResponse productResponse = productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));

        productService.update(productResponse.getProductId(), new ProductUpdateRequest("updateTest",50000, "updateTest2", 100));

        ProductResponse updatedProduct = productService.read(productResponse.getProductId());
        ProductStock productStock = productStockRepository.findProductStockByProducts_ProductId(productResponse.getProductId()).orElseThrow();

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertNotNull(productStock);

        Assertions.assertEquals("updateTest", updatedProduct.getName());
        Assertions.assertEquals("updateTest2", updatedProduct.getDescription());
        Assertions.assertEquals(50000, updatedProduct.getPrice());

        Assertions.assertEquals(100, productStock.getStock());
    }

    @Test
    void readAll(){
        productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));
        productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));
        productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));
        productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));
        productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));
        productService.create(new ProductCreateRequest("test1", 1000, "Test1", 1));

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductResponse> products = productService.readAll(pageRequest);

        Assertions.assertEquals(6, products.getTotalElements());
        Assertions.assertEquals(1, products.getTotalPages());
        Assertions.assertEquals("test1", products.getContent().getFirst().getName());
    }

    @Getter
    @AllArgsConstructor
    static class ProductCreateRequest extends make.otter.auctioninventorymanagement.domain.dto.ProductCreateRequest {
        private String name;
        private int price;
        private String description;
        private int stock;
    }

    @Getter
    @AllArgsConstructor
    static class ProductUpdateRequest extends make.otter.auctioninventorymanagement.domain.dto.ProductUpdateRequest {
        private String name;
        private int price;
        private String description;
        private int stock;
    }
}
