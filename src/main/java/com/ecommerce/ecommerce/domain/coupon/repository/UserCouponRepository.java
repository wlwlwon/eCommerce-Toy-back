package com.ecommerce.ecommerce.domain.coupon.repository;

import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.domain.UserCoupon;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    Optional<UserCoupon> findUserCouponByMemberAndCoupon(Member member, Coupon coupon);

}
