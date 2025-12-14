package make.otter.auctioninventorymanagement.service;

import lombok.RequiredArgsConstructor;
import make.otter.auctioninventorymanagement.domain.dto.ProductCreateRequest;
import make.otter.auctioninventorymanagement.domain.dto.ProductResponse;
import make.otter.auctioninventorymanagement.domain.dto.ProductUpdateRequest;
import make.otter.auctioninventorymanagement.domain.entity.ProductStock;
import make.otter.auctioninventorymanagement.domain.entity.Products;
import make.otter.auctioninventorymanagement.repository.ProductRepository;
import make.otter.auctioninventorymanagement.repository.ProductStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;
    private final LockedStockService lockedStockService;

    @Override
    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Products product = Products.create(
                request.getName(),
                request.getPrice(),
                request.getDescription()
        );
        productRepository.save(product);
        ProductStock productStock = ProductStock.create(product, request.getStock());
        productStockRepository.save(productStock);
        return ProductResponse.from(product);
    }

    @Override
    public Products update(Long productId, ProductUpdateRequest request) {
        Products product = productRepository.findById(productId)
                .orElseThrow();

        product.update(
                request.getName(),
                request.getPrice(),
                request.getDescription()
        );

        lockedStockService.changeStock(productId, request.getStock());
        return product;
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponse read(Long productId) {
        Products products = productRepository.findById(productId).orElseThrow();
        return ProductResponse.from(products);
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
    public Page<ProductResponse> readAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::from);
    }

}
