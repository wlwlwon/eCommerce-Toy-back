package com.ecommerce.ecommerce.domain.order.service;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.order.dto.OrderPurchaseResponseDTO;
import com.ecommerce.ecommerce.domain.order.dto.OrderRequestDTO;

import java.util.List;

public interface OrderService {

    void order(Member member, OrderRequestDTO orderRequestDto);

    List<OrderPurchaseResponseDTO> getOrderList(Member member);
}
