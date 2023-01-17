package com.ecommerce.ecommerce.domain.product;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product,Long> {


    List<Product> getProductsByKeyword(String keyword);

    Optional<Product> findByProductId(long id);

    List<Product> getProductsByIsRocket(boolean b, int startId, int listSize);

    List<Product> getProductsByIsRocketAndIsRocketFresh(boolean b, boolean b1, int startId, int listSize);

    List<Product> getProductsByIsRocketFresh(boolean b, int startId, int listSize);

    List<Product> getProductsByIsRocketAndIsRocketGlobal(boolean b, boolean b1, int startId, int listSize);

    List<Product> getProductsByIsRocketGlobal(boolean b, int startId, int listSize);
}
