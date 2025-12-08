package make.otter.auctioninventorymanagement.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import make.otter.auctioninventorymanagement.domain.entity.Products;
import make.otter.auctioninventorymanagement.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

@SpringBootTest
@Transactional
public class ProductServiceTest {
    @Autowired
    ProductService productService;

    @Autowired
    EntityManager em;

    @Test
    void createProduct() {
        Products products = productService.create("test1", "test1", 1000);

        Products findProduct = productService.findById(products.getProductId());

        Assertions.assertNotNull(findProduct);
        Assertions.assertEquals(products, findProduct);
    }

    @Test
    void updateProduct(){
        Products products = productService.create("test1", "Test1", 1000);

        productService.update(products.getProductId(), "updateTest", "updateTest2", 50000);

        Products updatedProduct = productService.findById(products.getProductId());

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals("updateTest", updatedProduct.getName());
        Assertions.assertEquals("updateTest2", updatedProduct.getDescription());
        Assertions.assertEquals(50000, updatedProduct.getPrice());
    }

    @Test
    void readAll(){
        productService.create("test1", "Test1", 1000);
        productService.create("test1", "Test1", 1000);
        productService.create("test1", "Test1", 1000);
        productService.create("test1", "Test1", 1000);
        productService.create("test1", "Test1", 1000);
        productService.create("test1", "Test1", 1000);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Products> products = productService.readAll(pageRequest);

        Assertions.assertEquals(6, products.getTotalElements());
        Assertions.assertEquals(1, products.getTotalPages());
        Assertions.assertEquals("test1", products.getContent().getFirst().getName());
    }
}
