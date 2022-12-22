package com.ecommerce.ecommerce.domain.member;

import com.ecommerce.ecommerce.domain.infra.MockMvcTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 처리 -입력값 정상")
    @Transactional
    void signUpSubmit_with_correct_input() throws Exception {

        MemberRequestDTO requestDTO = new MemberRequestDTO();
        requestDTO.setEmail("ryan2@email.com");
        requestDTO.setNickname("jiwon");
        requestDTO.setPassword("12345678");

        mockMvc.perform(post("/member/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestDTO)))
                .andExpect(status().isCreated());

        Optional<Member> member = memberRepository.findByEmail("ryan2@email.com");
        assertNotNull(member.get());

    }
    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

}