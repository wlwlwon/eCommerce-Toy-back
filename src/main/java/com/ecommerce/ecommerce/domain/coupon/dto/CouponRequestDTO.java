package com.ecommerce.ecommerce.domain.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
public class CouponRequestDTO {

    private String name;

    private Long minPrice;

    private Long discountPrice;

    private Long productId;

    private int maxCouponCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private Date expirationTime;

}
