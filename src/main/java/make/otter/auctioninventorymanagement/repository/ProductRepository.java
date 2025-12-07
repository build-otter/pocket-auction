package make.otter.auctioninventorymanagement.repository;

import make.otter.auctioninventorymanagement.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

}
