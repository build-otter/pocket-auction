package make.otter.auctioninventorymanagement.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private int price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Products() {
    }

    public static Products create(String name, int price, String description){
        Products product = new Products();
        product.name = name;
        product.price = price;
        product.description = description;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = LocalDateTime.now();
        return product;
    }

    public void update(String name, int price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Products product = (Products) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
