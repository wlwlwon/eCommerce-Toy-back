package com.ecommerce.ecommerce.domain.member.service;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO);

    boolean isDuplicatedEmail(String email);

    Member getMember(String userName);

}
