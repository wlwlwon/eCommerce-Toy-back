package com.ecommerce.ecommerce.domain.cart.domain;

import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.stuff.Stuff;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private long productId;

    @Column
    private int productNum;

    @OneToMany(mappedBy = "cart")
    private List<Stuff> stuffs;

    @OneToOne
    private Member member;
}
