package com.ecommerce.ecommerce.domain.stuff.service;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StuffServiceImpl implements StuffService{
    @Override
    public Stuff makeStuff(SaveToCartRequest dto, Product product) {
        return Stuff.builder()
                .productNum(dto.getProductNum())
                .product(product)
                .build();
    }
}
