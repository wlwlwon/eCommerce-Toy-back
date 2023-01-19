package com.ecommerce.ecommerce.domain.coupon.service;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.domain.UserCoupon;
import com.ecommerce.ecommerce.domain.coupon.dto.CouponRequestDTO;
import com.ecommerce.ecommerce.domain.coupon.repository.CouponRepository;
import com.ecommerce.ecommerce.domain.coupon.repository.UserCouponRepository;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final ModelMapper modelMapper;

    @Override
    public Coupon createCoupon(CouponRequestDTO couponRequestDTO) {
        Coupon coupon = modelMapper.map(couponRequestDTO, Coupon.class);
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAvailableCoupons(){
        List<Coupon> coupons = couponRepository.findAll();

        List<Coupon> availableCoupons = coupons.stream()
                .filter(coupon -> (new Date()).before(coupon.getExpirationTime()))
                .collect(Collectors.toList());

        return availableCoupons;
    }

    @Override
    @Transactional
    public void saveCoupon(Long id, Member member) {
        if(!checkIsAvailableCoupon(id))
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");

        Optional<Coupon> coupon = couponRepository.findCouponById(id);
        int maxCouponCount = coupon.get().getMaxCouponCount();

        int issuedCouponCount = 0;
        if(checkIsAlreadyHave(coupon.get(), member)) {
            UserCoupon userCoupon = userCouponRepository.findUserCouponByMemberAndCoupon(member, coupon.get()).get();
            issuedCouponCount = userCoupon.getIssuedCount();
        }

        if(maxCouponCount == issuedCouponCount)
            throw new IllegalArgumentException("더 이상 발급이 불가능합니다.");

        if(!checkIsAlreadyHave(coupon.get(), member)){
            UserCoupon userCoupon = UserCoupon.builder()
                    .member(member)
                    .coupon(coupon.get())
                    .issuedCount(issuedCouponCount+1)
                    .build();
            userCouponRepository.save(userCoupon);
        }
        else {
            UserCoupon userCoupon = userCouponRepository.findUserCouponByMemberAndCoupon(member, coupon.get()).get();
            userCoupon.setIssuedCount(issuedCouponCount+1);
            userCouponRepository.save(userCoupon);
        }
    }

    @Override
    public boolean checkIsAvailableCoupon(long id){
        Date expirationTime = couponRepository.findCouponById(id).get().getExpirationTime();
        return (new Date()).before(expirationTime);
    }
    @Override
    public boolean checkIsAvailableCoupon(Coupon coupon){
        Date expirationTime = coupon.getExpirationTime();
        return (new Date()).before(expirationTime);
    }

    @Override
    public boolean checkIsAlreadyHave(Coupon coupon, Member member) {
        return userCouponRepository.findUserCouponByMemberAndCoupon(member,coupon).isPresent();
    }

    @Override
    public long getDiscountPriceByCoupon(Member member, Optional<Coupon> coupon, Cart cartProducts, long totalProductPrice){
        if(coupon.isEmpty())
            return 0;

        if(!checkIsAlreadyHave(coupon.get(), member))
            throw new IllegalArgumentException("발급받지 않은 쿠폰입니다.");
        if(!checkIsAvailableCoupon(coupon.get()))
            throw new IllegalArgumentException("기간이 지난 쿠폰입니다.");

        List<Long> productsId = cartProducts.getStuff().stream()
                .map(cartStuff -> cartStuff.getProduct().getId())
                .collect(Collectors.toList());

        if(!productsId.contains(coupon.get().getProductId()))
            throw new IllegalArgumentException("상품에 적용되는 쿠폰이 아닙니다.");
        if(coupon.get().getMinPrice() > totalProductPrice)
            throw new IllegalArgumentException("최소주문금액을 만족하지 못합니다.");

        Optional<UserCoupon> userCoupon = userCouponRepository.findUserCouponByMemberAndCoupon(member, coupon.get());
        if(userCoupon.get().getIssuedCount() - userCoupon.get().getUseCount() <= 0)
            throw new IllegalArgumentException("이미 사용 완료한 쿠폰입니다.");

        return coupon.get().getDiscountPrice();
    }

    @Override
    public void increaseUseCount(Member member, Coupon coupon){
        Optional<UserCoupon> userCoupon = userCouponRepository.findUserCouponByMemberAndCoupon(member, coupon);
        userCoupon.get().setUseCount(userCoupon.get().getUseCount()+1);
        userCouponRepository.save(userCoupon.get());
    }

    @Override
    public Optional<Coupon> findCoupon(long id){
        return couponRepository.findCouponById(id);
    }

}
