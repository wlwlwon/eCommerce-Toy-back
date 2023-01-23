package com.ecommerce.ecommerce.domain.product.service;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.ecommerce.ecommerce.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("배송 유형이 로켓일시 로켓 배송 가능한 상품 목록을 가져온다.")
    @Test
    public void getProductsByIsRocket() {
        // given
        final ProductsRequest dto = ProductsRequest.builder()
                .deliveryTypeEnum(ROCKET)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).findProductByRocket(any(Boolean.class));
    }

    @DisplayName("배송 유형이 로켓 프레쉬일시 로켓 프레쉬 배송 가능한 상품 목록을 가져온다.")
    @Test
    public void getProductsByIsRocketFresh() {
        // given
        final ProductsRequest dto = ProductsRequest.builder()
                .deliveryTypeEnum(ROCKET_FRESH)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).findProductByRocketFresh(any(Boolean.class));
    }

    @DisplayName("배송 유형이 로켓 직구일시 로켓 직구 배송 가능한 상품 목록을 가져온다.")
    @Test
    public void getProductsByIsRocketGlobal() {
        // given
        final ProductsRequest dto = ProductsRequest.builder()
                .deliveryTypeEnum(ROCKET_GLOBAL)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).findProductByRocketGlobal(any(Boolean.class));
    }

    @DisplayName("배송 유형이 로켓 프레쉬, 로켓 배송이고 로켓 프레쉬와 로켓 배송 모두 가능한 전체 상품을 가져온다.")
    @Test
    public void getProductsByIsRocketAndIsRocketFresh() {
        // given
        final ProductsRequest dto = ProductsRequest.builder()
                .deliveryTypeEnum(ROCKET_FRESH)
                .rocket(true)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).findProductByRocketAndRocketFresh(any(Boolean.class),any(Boolean.class));
    }

    @DisplayName("배송 유형이 로켓 직구,로켓 배송이고 로켓 직구와 로켓 배송 모두 가능한 전체 상품을 가져온다.")
    @Test
    public void getProductsByIsRocketAndIsRocketGlobal() {
        // given
        final ProductsRequest dto = ProductsRequest.builder()
                .deliveryTypeEnum(ROCKET_GLOBAL)
                .rocket(true)
                .build();

        // when
        productService.getProducts(dto);

        // then
        then(productRepository).should(times(1)).findProductByRocketAndRocketGlobal(any(Boolean.class),any(Boolean.class));
    }
}