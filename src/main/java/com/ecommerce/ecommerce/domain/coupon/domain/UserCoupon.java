package com.ecommerce.ecommerce.domain.coupon.domain;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    @OneToOne
    private Coupon coupon;

//    private Long userId;
//
//    private Long couponId;

    private int useCount;

    private int issuedCount;
}
