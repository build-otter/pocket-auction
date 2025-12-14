package make.otter.auctioninventorymanagement.service;

import make.otter.auctioninventorymanagement.domain.dto.ProductCreateRequest;
import make.otter.auctioninventorymanagement.domain.dto.ProductResponse;
import make.otter.auctioninventorymanagement.domain.dto.ProductUpdateRequest;
import make.otter.auctioninventorymanagement.domain.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductCreateRequest request);
    Products update(Long productId, ProductUpdateRequest request);
    void delete(Long productId);

    ProductResponse read(Long productId);
    Products findByName(String name);
    List<Products> readAll(long page, long limit);
    Page<ProductResponse> readAll(Pageable pageable);
}
