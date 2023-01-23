package com.ecommerce.ecommerce.domain.address.controller;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.address.dto.AddressRequestDTO;
import com.ecommerce.ecommerce.domain.address.service.AddressService;
import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.dto.CouponRequestDTO;
import com.ecommerce.ecommerce.domain.global.common.StatusEnum;
import com.ecommerce.ecommerce.domain.global.common.SuccessResponse;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final MemberService memberService;

    @PostMapping("/add")
    public SuccessResponse addAddress(@AuthenticationPrincipal UserPrincipal member, @RequestBody AddressRequestDTO addressRequestDTO){
        Member loginMember = memberService.getMember(member.getUsername());
        Address address = addressService.addAddress(loginMember,addressRequestDTO);

        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("사용가능한 쿠폰 목록 가져오기 성공")
                .data(address)
                .build();
    }
}
