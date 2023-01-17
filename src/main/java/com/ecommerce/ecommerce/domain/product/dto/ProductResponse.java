package com.ecommerce.ecommerce.domain.product.dto;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private long price;

    @NotNull
    private String mainImg;

    @NotNull
    private float score;


    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .mainImg(product.getMainImg())
                .score(product.getScore())
                .build();
    }

    public static List<ProductResponse> toList(List<Product> products) {
        return products.stream()
                .map(ProductResponse::toResponse)
                .collect(Collectors.toList());
    }
}
