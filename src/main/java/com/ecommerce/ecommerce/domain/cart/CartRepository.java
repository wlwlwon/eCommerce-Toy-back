package com.ecommerce.ecommerce.domain.cart;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.stuff.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CartRepository extends JpaRepository<Cart,Long> {

    void updateProductNum(long productId, int i);

    Optional<Cart> findByMemberAndStuff(Member member, Stuff stuff);

    Stuff getByStuff(Stuff stuff);
}
