package com.ecommerce.ecommerce.domain.member;

public interface MemberService {

    MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO) ;

    boolean isDuplicatedEmail(String email);
}
