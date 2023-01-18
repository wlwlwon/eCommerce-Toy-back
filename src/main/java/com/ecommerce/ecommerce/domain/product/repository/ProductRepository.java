package com.ecommerce.ecommerce.domain.product.repository;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findByNameLike(@Param("name") String name);

    Optional<Product> findById(long id);

    List<Product> findProductByRocket(boolean b);

    List<Product> findProductByRocketAndRocketFresh(boolean b, boolean b1);

    List<Product> findProductByRocketFresh(boolean b);

    List<Product> findProductByRocketAndRocketGlobal(boolean b, boolean b1);

    List<Product> findProductByRocketGlobal(boolean b);
}
