package com.ecommerce.ecommerce.domain.stuff.service;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;

public interface StuffService {
    Stuff makeStuff(SaveToCartRequest dto, Product product);
}
