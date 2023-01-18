package com.ecommerce.ecommerce.domain.member;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
    @Test
    @DisplayName("회원가입 처리 -중복 email")
    @Transactional
    void signUpSubmit_with_conflict_input() throws Exception {

        MemberRequestDTO requestDTO = new MemberRequestDTO();
        requestDTO.setEmail("jiwon@email.com");
        requestDTO.setNickname("jiwon");
        requestDTO.setPassword("12345678");

        mockMvc.perform(post("/member/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestDTO)))
                .andExpect(status().isConflict());

    }
    private <T> String toJson(T data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

}