package com.ecommerce.ecommerce.domain.coupon.controller;

import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("이벤트/쿠폰을 선택하면 사용 가능한 쿠폰 목록을 보여준다.")
    @Test
    public void getAvailableCoupons() throws Exception{
        //when
        final ResultActions actions = mockMvc.perform(get("/available-coupons"))
                .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("사용자는 사용 가능한 쿠폰 저장에 성공한다.")
    @Test
    public void saveAvailableCoupons() throws Exception{
        // given

        MemberRequestDTO requestDTO = new MemberRequestDTO();
        requestDTO.setEmail("jiwon@email.com");
        requestDTO.setNickname("jiwon");
        requestDTO.setPassword("12345678");
        requestDTO.setPhoneNumber("01012341234");
        requestDTO.setName("kimjiwon");

        mockMvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestDTO)));


        //when
        final ResultActions actions = mockMvc.perform(post("/available-coupons/{id}",3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }
    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }
}