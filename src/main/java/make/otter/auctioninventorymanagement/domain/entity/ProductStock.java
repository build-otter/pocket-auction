package make.otter.auctioninventorymanagement.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Entity
@Getter
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Products products;

    private int stock;

    @Version
    private int version; //낙관적 락 사용시

    private LocalDateTime updatedAt;

    protected ProductStock(){
    }

    public static ProductStock create(Products product, int stock){
        ProductStock productStock = new ProductStock();
        productStock.products = product;
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

    public void changeStock(int stock){
        if(stock <= 0) throw new IllegalArgumentException("Product Stock cannot be negative");
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductStock{" +
                "stockId=" + stockId +
                ", product=" + products +
                ", stock=" + stock +
                ", version=" + version +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
