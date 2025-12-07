package make.otter.auctioninventorymanagement.service;

import lombok.RequiredArgsConstructor;
import make.otter.auctioninventorymanagement.domain.Products;
import make.otter.auctioninventorymanagement.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public Products create(String name, String description, int price) {
        Products product = Products.create(name, price, description);
        productRepository.save(product);
        return product;
    }

    @Override
    public Products update(Long productId, String name, String description, int price) {
        Products product = productRepository.findById(productId)
                .orElseThrow();
        product.update(name, price, description);
        return product;
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Products findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Override
    public Products findByName(String name) {
        return null;
    }

    @Override
    public List<Products> readAll(long page, long limit) {
        return List.of();
    }

    @Override
    public Page<Products> readAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
