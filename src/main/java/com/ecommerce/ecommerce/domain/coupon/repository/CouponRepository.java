package com.ecommerce.ecommerce.domain.coupon.repository;

import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    List<Coupon> findAll();

    Optional<Coupon> findCouponById(long id);

}
