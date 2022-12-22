package com.ecommerce.ecommerce.domain.member;

import lombok.Getter;

@Getter
public class MemberRequestDTO {

    private Long id;

    private String email;

    private String nickname;

    private String accessToken;

    private String refreshToken;
}
