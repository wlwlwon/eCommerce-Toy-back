package com.ecommerce.ecommerce.domain.coupon.domain;


import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long minPrice;

    private Long discountPrice;

    private Long productId;

    private int maxCouponCount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;
}
