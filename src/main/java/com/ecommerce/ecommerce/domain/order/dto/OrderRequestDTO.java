package com.ecommerce.ecommerce.domain.order.dto;

import com.ecommerce.ecommerce.domain.payment.dto.PaymentType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequestDTO {

    @NotNull
    private PaymentType type;

    @NotNull
    private String receiverName;

    @NotNull
    private String receiverPhone;

    @Max(50)
    @NotNull
    private String receiverRequest;

    private long couponId;
}
