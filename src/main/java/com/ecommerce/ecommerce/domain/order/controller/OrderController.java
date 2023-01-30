package com.ecommerce.ecommerce.domain.order.controller;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.global.common.StatusEnum;
import com.ecommerce.ecommerce.domain.global.common.SuccessResponse;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import com.ecommerce.ecommerce.domain.order.domain.OrderPurchase;
import com.ecommerce.ecommerce.domain.order.dto.OrderPurchaseResponseDTO;
import com.ecommerce.ecommerce.domain.order.dto.OrderRequestDTO;
import com.ecommerce.ecommerce.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/get")
    public SuccessResponse orderList(@AuthenticationPrincipal UserPrincipal member){
        Member loginMember = memberService.getMember(member.getUsername());
        List<OrderPurchaseResponseDTO> orderList = orderService.getOrderList(loginMember);
        return SuccessResponse.builder()
                .status(StatusEnum.CREATED)
                .message("주문 리스트 가져오기")
                .data(orderList)
                .build();
    }

    @Transactional
    @PostMapping("/orders")
    public SuccessResponse order(@RequestBody OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserPrincipal member) {
        Member getMember = memberService.getMember(member.getUsername());
        orderService.order(getMember, orderRequestDTO);
        return SuccessResponse.builder()
                .status(StatusEnum.CREATED)
                .message("주문하기 성공")
                .build();
    }

}
