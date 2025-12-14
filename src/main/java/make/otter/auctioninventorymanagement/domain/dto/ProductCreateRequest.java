package make.otter.auctioninventorymanagement.domain.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductCreateRequest {
    private String name;
    private int price;
    private String description;
    private int stock;
}
