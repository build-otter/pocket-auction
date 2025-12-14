package make.otter.auctioninventorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class AuctionInventoryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionInventoryManagementApplication.class, args);
    }

}
