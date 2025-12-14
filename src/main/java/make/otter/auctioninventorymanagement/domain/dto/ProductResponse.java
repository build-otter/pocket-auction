package make.otter.auctioninventorymanagement.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import make.otter.auctioninventorymanagement.domain.entity.Products;

import java.time.LocalDateTime;

@Getter
@ToString
public class ProductResponse {
    private long productId;
    private String name;
    private int price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse from(Products products) {
        ProductResponse response = new ProductResponse();
        response.productId = products.getProductId();
        response.name = products.getName();
        response.price = products.getPrice();
        response.description = products.getDescription();
        response.createdAt = products.getCreatedAt();
        response.updatedAt = products.getUpdatedAt();
        return response;
    }
}
