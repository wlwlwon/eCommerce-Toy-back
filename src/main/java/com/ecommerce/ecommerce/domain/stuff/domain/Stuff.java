package com.ecommerce.ecommerce.domain.stuff.domain;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.order.domain.OrderProduct;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Stuff {

    @Id
    @GeneratedValue
    private long id;

    private int productNum;

    @OneToOne
    private Product product;

}
