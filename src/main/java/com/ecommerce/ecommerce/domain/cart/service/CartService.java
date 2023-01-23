package com.ecommerce.ecommerce.domain.cart.service;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;

import java.util.List;

public interface CartService {

    List<Stuff> getCart(Member loginMember);

    void saveProduct(Member loginMember, SaveToCartRequest dto);

    Cart findOrCreateNewCart(Member member);

}
