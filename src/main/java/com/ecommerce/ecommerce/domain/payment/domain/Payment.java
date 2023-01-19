package com.ecommerce.ecommerce.domain.payment.domain;

import com.ecommerce.ecommerce.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long id;

    private int type;

    private long discountPrice;

    private long totalPrice;

    private boolean status;

}
