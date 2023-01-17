package com.ecommerce.ecommerce.domain.stuff;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface StuffRepository extends JpaRepository<Stuff,Long> {
    Optional<Stuff> findByCartAndProduct(Cart cart, Product product);
}
