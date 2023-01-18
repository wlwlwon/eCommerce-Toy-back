package com.ecommerce.ecommerce.domain.product.dto;

import com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductsRequest {

    @NotNull
    private DeliveryTypeEnum deliveryTypeEnum;

    @Nullable
    private boolean rocket;

}
