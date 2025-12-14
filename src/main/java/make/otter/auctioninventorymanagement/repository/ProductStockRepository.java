package make.otter.auctioninventorymanagement.repository;

import jakarta.persistence.LockModeType;
import make.otter.auctioninventorymanagement.domain.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
            """
            SELECT ps FROM ProductStock ps WHERE ps.products.productId = :productId
            """
    )
    Optional<ProductStock> findByIdWithXLock(@Param("productId") Long productId);

    Optional<ProductStock> findProductStockByProducts_ProductId(@Param("productId") Long productId);
}
