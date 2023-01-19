package com.ecommerce.ecommerce.domain.member.domain;


import com.ecommerce.ecommerce.domain.BaseTimeEntity;
import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.coupon.domain.UserCoupon;
import com.ecommerce.ecommerce.domain.friend.Friend;
import com.ecommerce.ecommerce.domain.member.Role;
import com.ecommerce.ecommerce.domain.order.domain.OrderPurchase;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 13)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role authority;

    @OneToOne
    private Cart cart;

    @Transient
    private String accessToken;

    @Column(length = 1000)
    private String refreshToken;

    @OneToMany(mappedBy = "member")
    private Set<Friend> friendSet = new HashSet<>();

    @OneToMany(mappedBy = "member")
    private List<UserCoupon> userCouponList;

    @OneToOne
    private OrderPurchase order;

    @OneToOne
    private Address address;


}
