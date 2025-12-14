package make.otter.auctioninventorymanagement.service;


public interface StockService {
    void changeStock(Long productId, int stock);

    void decreaseWithPessimisticLock(Long productId, int quantity);

    void decreaseWithOptimisticLock(Long productId, int quantity);
}
