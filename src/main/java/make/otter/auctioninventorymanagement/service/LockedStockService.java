package make.otter.auctioninventorymanagement.service;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import make.otter.auctioninventorymanagement.domain.entity.ProductStock;
import make.otter.auctioninventorymanagement.repository.ProductStockRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockedStockService implements StockService {
    private final ProductStockRepository stockRepository;

    private final static int RETRY_COUNT = 3;

    @Override
    @Transactional
    public void changeStock(Long productId, int stock) {
        ProductStock productStock = stockRepository.findProductStockByProducts_ProductId(productId).orElseThrow();
        productStock.changeStock(stock);
    }

    @Override
    @Transactional
    public void decreaseWithPessimisticLock(Long productId, int quantity) {
        ProductStock productStock = stockRepository.findByIdWithXLock(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        productStock.decreaseStock(quantity);
        stockRepository.saveAndFlush(productStock);
    }

    @Override
    @Transactional
    @Retryable(
            value = ObjectOptimisticLockingFailureException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    public void decreaseWithOptimisticLock(Long productId, int quantity) {
        ProductStock productStock = stockRepository.findProductStockByProducts_ProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));
        productStock.decreaseStock(quantity);
    }
}
