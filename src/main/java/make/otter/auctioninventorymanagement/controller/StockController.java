package make.otter.auctioninventorymanagement.controller;

import lombok.RequiredArgsConstructor;
import make.otter.auctioninventorymanagement.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping("/product/{productId}/stock/pessimistic")
    public ResponseEntity<Void> decreaseStockWithPessimistic(
            @PathVariable Long productId, @RequestBody int quantity) {
        stockService.decreaseWithPessimisticLock(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/product/{productId}/stock/optimistic")
    public ResponseEntity<Void> decreaseStockWithOptimistic(
            @PathVariable Long productId, @RequestBody int quantity) {
        stockService.decreaseWithOptimisticLock(productId, quantity);
        return ResponseEntity.ok().build();
    }
}
