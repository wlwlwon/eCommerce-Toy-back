package com.ecommerce.ecommerce.domain.cart.controller;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.cart.service.CartServiceImpl;
import com.ecommerce.ecommerce.domain.global.common.StatusEnum;
import com.ecommerce.ecommerce.domain.global.common.SuccessResponse;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartService;
    private final MemberService memberService;

    @PostMapping("/carts")
    public SuccessResponse saveProductsToCart(@Valid @RequestBody  SaveToCartRequest dto, @AuthenticationPrincipal UserPrincipal member){
        Member loginMember = memberService.getMember(member.getUsername());
        cartService.saveProduct(loginMember, dto);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("장바구니에 상품 담기 성공")
                .build();
    }
}
