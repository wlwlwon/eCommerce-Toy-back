package com.ecommerce.ecommerce.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRequestDTO {

    private String email;

    private String password;

    private String nickname;

    private String accessToken;

    private String refreshToken;
}
