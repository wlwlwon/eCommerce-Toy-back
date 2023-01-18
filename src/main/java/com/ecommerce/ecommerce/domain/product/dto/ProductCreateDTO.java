package com.ecommerce.ecommerce.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {

    private long categoryId;

    private String name;

    private long price;

    private String mainImg;

    private String detailImg;

    private long stock;

    private Float score;

    private long deliveryFee;

    private Boolean rocket;

    private Boolean rocketFresh;

    private Boolean rocketGlobal;
}
