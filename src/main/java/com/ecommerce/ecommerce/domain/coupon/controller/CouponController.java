package com.ecommerce.ecommerce.domain.coupon.controller;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.domain.UserCoupon;
import com.ecommerce.ecommerce.domain.coupon.dto.CouponRequestDTO;
import com.ecommerce.ecommerce.domain.coupon.dto.UserCouponResponseDTO;
import com.ecommerce.ecommerce.domain.coupon.service.CouponService;
import com.ecommerce.ecommerce.domain.global.common.StatusEnum;
import com.ecommerce.ecommerce.domain.global.common.SuccessResponse;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final MemberService memberService;

    @PostMapping("/create-coupon")
    public SuccessResponse createCoupon(@AuthenticationPrincipal UserPrincipal member, @RequestBody CouponRequestDTO couponRequestDTO) {
        Coupon coupon = couponService.createCoupon(couponRequestDTO);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("쿠폰 생성 성공")
                .data(coupon)
                .build();
    }

    @GetMapping("/available-coupons")
    public SuccessResponse getAvailableCoupons(@AuthenticationPrincipal UserPrincipal member) {
        Member loginMember = memberService.getMember(member.getUsername());
        List<UserCouponResponseDTO> coupons = couponService.getAvailableCoupons(loginMember);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("사용가능한 쿠폰 목록 가져오기 성공")
                .data(coupons)
                .build();
    }

    @PostMapping("/available-coupons/{id}")
    public SuccessResponse saveCoupon(@PathVariable("id") final Long id, @AuthenticationPrincipal UserPrincipal member) {
        couponService.saveCoupon(id, memberService.getMember(member.getUsername()));
        return SuccessResponse.builder()
                .status(StatusEnum.CREATED)
                .message("쿠폰받기 성공")
                .build();
    }
}
