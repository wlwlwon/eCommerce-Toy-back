package com.ecommerce.ecommerce.domain.order.service;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.order.domain.OrderPurchase;
import com.ecommerce.ecommerce.domain.order.domain.OrderProduct;
import com.ecommerce.ecommerce.domain.order.dto.OrderRequestDTO;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;

public interface OrderService {

    void order(Member member, OrderRequestDTO orderRequestDto);

}
