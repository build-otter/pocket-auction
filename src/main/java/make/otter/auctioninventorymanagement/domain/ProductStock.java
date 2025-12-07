package make.otter.auctioninventorymanagement.domain;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Entity
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @OneToOne(cascade = CascadeType.ALL)
    private Products product;

    private int stock;

    @Version
    private int version; //낙관적 락 사용시

    private LocalDateTime updatedAt;

    protected ProductStock(){
    }

    public static ProductStock create(Products product, int stock){
        ProductStock productStock = new ProductStock();
        productStock.product = product;
        productStock.stock = stock;
        productStock.updatedAt = LocalDateTime.now();
        return productStock;
    }

    public void decreaseStock(int quantity){
        if(quantity <= 0 || quantity > stock){
            log.error("Invalid stock quantity quantity : {} stock : {}", quantity, this.stock);
            throw new IllegalArgumentException("Product Decrease quantity Error");
        }
        this.stock -= quantity;
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "stockId=" + stockId +
                ", product=" + product +
                ", stock=" + stock +
                ", version=" + version +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
