package com.ecommerce.ecommerce.domain.order.domain;

import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue
    private long id;

    private long productId;

    private int productNum;

}
