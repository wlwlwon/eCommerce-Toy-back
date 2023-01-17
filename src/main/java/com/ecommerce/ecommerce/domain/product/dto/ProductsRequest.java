package com.ecommerce.ecommerce.domain.product.dto;

import com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter @Builder
public class ProductsRequest {

    @NotNull
    private DeliveryTypeEnum deliveryType;

    @Nullable
    private boolean rocket;

    @NotNull
    @Min(1)
    private int start;

    @NotNull
    @Min(1) @Max(100)
    private int listSize;
}
