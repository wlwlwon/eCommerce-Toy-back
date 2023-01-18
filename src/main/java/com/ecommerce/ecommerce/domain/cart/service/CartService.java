package com.ecommerce.ecommerce.domain.cart.service;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;

public interface CartService {

    void saveProduct(Member member, SaveToCartRequest dto);

}
