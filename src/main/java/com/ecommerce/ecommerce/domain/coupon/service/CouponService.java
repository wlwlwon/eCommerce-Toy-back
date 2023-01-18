package com.ecommerce.ecommerce.domain.coupon.service;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    List<Coupon> getAvailableCoupons();

    void saveCoupon(Long id, Member member);

    boolean checkIsAvailableCoupon(long id);

    boolean checkIsAvailableCoupon(Coupon coupon);

    boolean checkIsAlreadyHave(Coupon coupon, Member member);

    long getDiscountPriceByCoupon(Member member, Optional<Coupon> coupon, Cart cartProducts, long totalProductPrice);

    void increaseUseCount(Member member, Coupon coupon);

    Optional<Coupon> findCoupon(long id);
}
