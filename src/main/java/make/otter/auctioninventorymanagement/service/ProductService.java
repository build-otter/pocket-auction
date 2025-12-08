package make.otter.auctioninventorymanagement.service;

import make.otter.auctioninventorymanagement.domain.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Products create(String name, String description, int price);
    Products update(Long productId, String name, String description, int price);
    void delete(Long productId);

    Products findById(Long productId);
    Products findByName(String name);
    List<Products> readAll(long page, long limit);
    Page<Products> readAll(Pageable pageable);
}
