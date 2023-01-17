package com.ecommerce.ecommerce.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class SaveToCartRequest {
    @NotNull
    private long productId;

    @NotNull
    private int productNum;

}
