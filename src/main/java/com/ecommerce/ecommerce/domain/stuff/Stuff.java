package com.ecommerce.ecommerce.domain.stuff;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stuff {

    @Id
    @GeneratedValue
    private long id;

    private int productNum;

    @ManyToOne
    private Cart cart;

    @OneToOne
    private Product product;
}
