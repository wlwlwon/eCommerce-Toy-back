package com.ecommerce.ecommerce.domain.order.dto;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderPurchaseResponseDTO {

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
