package com.ecommerce.ecommerce.domain.cart.repository;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByMember(Member member);
}
