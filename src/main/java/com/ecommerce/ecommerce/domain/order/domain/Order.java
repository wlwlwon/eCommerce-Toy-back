package com.ecommerce.ecommerce.domain.order.domain;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProductList;

    private String consumerName;

    private String consumerPhone;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverRequest;

    private boolean status;

    private ZonedDateTime createdAt;

    private long paymentId;
}
