package com.ecommerce.ecommerce.domain.member.dto;

import com.ecommerce.ecommerce.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter @Setter
public class MemberRequestDTO {

    private String email;

    private String password;

    private String nickname;

    private String name;

    private String phoneNumber;
    
    private Role authority;

    private String accessToken;

    private String refreshToken;
}
