package make.otter.auctioninventorymanagement.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import make.otter.auctioninventorymanagement.domain.StockStatus;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
public class StockDecreaseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    @ManyToOne
    private Products product;
    @ManyToOne
    private ProductStock productStock;
    private int quantity;
    private StockStatus stockStatus;
    private String strategy;
    private int elapsedMs;
    private String error_msg;
    private LocalDateTime createdAt;

    protected StockDecreaseLog() {
    }

    @Override
    public String toString() {
        return "StockDecreaseLog{" +
                "logId=" + logId +
                ", product=" + product +
                ", productStock=" + productStock +
                ", quantity=" + quantity +
                ", stockStatus=" + stockStatus +
                ", strategy='" + strategy + '\'' +
                ", elapsedMs=" + elapsedMs +
                ", error_msg='" + error_msg + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
