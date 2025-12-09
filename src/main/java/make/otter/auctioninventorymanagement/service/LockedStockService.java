package make.otter.auctioninventorymanagement.service;

import jakarta.persistence.LockTimeoutException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import make.otter.auctioninventorymanagement.domain.entity.ProductStock;
import make.otter.auctioninventorymanagement.repository.StockRepository;
import org.hibernate.PessimisticLockException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockedStockService implements StockService {
    private final StockRepository stockRepository;

    private final static int RETRY_COUNT = 3;

    @Override
    @Transactional
    public void decrease(Long productId, int quantity) {
        int retryCount = 0;

        while(true){
            try {
                decreaseWithPessimisticLock(productId, quantity);
                return;
            }catch (PessimisticLockException | LockTimeoutException e){
                log.error("[LockedStockService] Pessimistic Lock Exception : {}", e.getMessage());
                if(retryCount >= RETRY_COUNT){
                    throw new IllegalStateException("[LockedStockService] Pessimistic Lock Exceed Retry Count");
                }
                retryCount++;
                sleep(retryCount * 50);
            }
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    private void decreaseWithPessimisticLock(Long productId, int quantity) {
        ProductStock productStock = stockRepository.findByIdWithXLock(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        productStock.decreaseStock(quantity);

        stockRepository.saveAndFlush(productStock);
    }

    private void decreaseWithOptimisticLock(Long productId, int quantity) {
        ProductStock productStock = stockRepository.findProductStockByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        productStock.decreaseStock(quantity);
    }
}
