package com.ecommerce.ecommerce.domain.product.controller;

import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum.ROCKET;
import static com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum.ROCKET_FRESH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("로켓 배송을 선택시 로켓 배송 가능 상품 목록 가져오기.")
    @Test
    public void getProductsIsRocket() throws Exception {
        // given
        ProductsRequest dto = new ProductsRequest();
        dto.setDeliveryTypeEnum(ROCKET);

        // when
        final ResultActions actions = mockMvc.perform(get("/product/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto))
                        )
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("로켓 프레쉬 배송과 로켓 배송 선택시 로켓 프레쉬와 로켓 배송 모두 가능한 상품 가져오기.")
    @Test
    public void getProductsIsRocketAndIsRocketFresh() throws Exception {
        // given
        ProductsRequest dto = new ProductsRequest();
        dto.setDeliveryTypeEnum(ROCKET_FRESH);
        dto.setRocket(true);

        // when
        final ResultActions actions = mockMvc.perform(get("/product/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(toJson(dto))
                        )
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("키워드로 검색하면 키워드를 포함한 상품 목록을 가져온다.")
    @Test
    public void searchProductsByKeyword() throws Exception {
        // given
        final String name = "복숭아";

        // when
        final ResultActions actions = mockMvc.perform(get("/product/search")
                        .param("name", name))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }
    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}