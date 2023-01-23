package com.ecommerce.ecommerce.domain.cart.domain;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Stuff> stuffList;

    @OneToOne
    private Member member;
}
