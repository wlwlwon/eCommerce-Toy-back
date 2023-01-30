package com.ecommerce.ecommerce.domain.order.domain;

import com.ecommerce.ecommerce.domain.BaseTimeEntity;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class OrderPurchase extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Member member;

    @OneToMany
    private List<Product> productList;

    private String consumerName;

    private String consumerPhone;

    private String receiverName;

    private String receiverAddress;

    private String receiverPhone;

    private String receiverRequest;

    private boolean status;

    private long paymentId;
}
