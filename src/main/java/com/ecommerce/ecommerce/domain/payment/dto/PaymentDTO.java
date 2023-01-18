package com.ecommerce.ecommerce.domain.payment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class PaymentDTO {

    @NotNull
    private PaymentType type;

    private long discountPrice;

    @NotNull
    private long totalPrice;
}
