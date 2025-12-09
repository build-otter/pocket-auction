package make.otter.auctioninventorymanagement.service;


public interface StockService {

    void decrease(Long productId, int quantity);
}
