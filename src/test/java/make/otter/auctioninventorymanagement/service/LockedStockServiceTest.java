package make.otter.auctioninventorymanagement.service;

import make.otter.auctioninventorymanagement.domain.entity.ProductStock;
import make.otter.auctioninventorymanagement.domain.entity.Products;
import make.otter.auctioninventorymanagement.repository.ProductRepository;
import make.otter.auctioninventorymanagement.repository.ProductStockRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LockedStockServiceTest {
    @Autowired
    LockedStockService stockService;

    @Autowired
    ProductStockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    private static final int THREAD_COUNT = 100;
    @Autowired
    private ProductStockRepository productStockRepository;

    @Test
    void decreaseWithPessimisticLock() throws InterruptedException {
        Products products = Products.create("test1", 1000, "test1");

        productRepository.save(products);

        ProductStock productStock = ProductStock.create(products, 100);

        productStockRepository.save(productStock);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        for(int i = 1; i <= 100; i++){
            executorService.execute(() -> {
                try {
                    stockService.decreaseWithPessimisticLock(products.getProductId(), 1);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();
        ProductStock decreaseStock = productStockRepository
                .findProductStockByProducts_ProductId(products.getProductId()).orElseThrow();
        Assertions.assertEquals(0, decreaseStock.getStock());
    }

    @Test
    void decreaseWithOptimisticLock() throws InterruptedException {
        Products products = Products.create("test1", 1000, "test1");

        productRepository.save(products);

        ProductStock productStock = ProductStock.create(products, 100);

        productStockRepository.save(productStock);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        for(int i = 1; i <= 100; i++){
            executorService.execute(() -> {
                try {
                    stockService.decreaseWithOptimisticLock(products.getProductId(), 1);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();
        ProductStock decreaseStock = productStockRepository
                .findProductStockByProducts_ProductId(products.getProductId()).orElseThrow();
        Assertions.assertEquals(0, decreaseStock.getStock());
    }
}