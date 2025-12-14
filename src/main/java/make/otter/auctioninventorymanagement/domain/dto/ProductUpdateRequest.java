package make.otter.auctioninventorymanagement.domain.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductUpdateRequest {
    private Long productId;
    private String name;
    private String description;
    private int price;
    private int stock;
}
