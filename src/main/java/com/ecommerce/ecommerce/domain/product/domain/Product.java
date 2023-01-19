package com.ecommerce.ecommerce.domain.product.domain;

import com.ecommerce.ecommerce.domain.BaseTimeEntity;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private long categoryId;

    @Column
    private String name;

    @Column
    private long price;

    @Column
    private String mainImg;

    @Column
    private String detailImg;

    @Column
    private long stock;

    @Column
    private Float score;

    @Column
    private long deliveryFee;

    @Column
    private Boolean rocket;

    @Column
    private Boolean rocketFresh;

    @Column
    private Boolean rocketGlobal;

}
