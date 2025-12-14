package make.otter.auctioninventorymanagement.controller;

import lombok.RequiredArgsConstructor;
import make.otter.auctioninventorymanagement.domain.dto.ProductCreateRequest;
import make.otter.auctioninventorymanagement.domain.dto.ProductResponse;
import make.otter.auctioninventorymanagement.domain.dto.ProductUpdateRequest;
import make.otter.auctioninventorymanagement.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> read(@PathVariable Long productId){
        return ResponseEntity.ok().body(
                productService.read(productId)
        );
    }

    @GetMapping("/product")
    public ResponseEntity<Page<ProductResponse>> readAll(Pageable pageable){
        return ResponseEntity.ok().body(
                productService.readAll(pageable)
        );
    }

    @PostMapping("/product")
    public ResponseEntity<Void> create(@RequestBody ProductCreateRequest request){
        productService.create(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Void> update(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest request){
        productService.update(productId, request);
        return ResponseEntity.noContent().build();
    }
}
