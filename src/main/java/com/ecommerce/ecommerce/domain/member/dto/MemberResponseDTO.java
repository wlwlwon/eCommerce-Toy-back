package com.ecommerce.ecommerce.domain.member.dto;

import com.ecommerce.ecommerce.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberResponseDTO {

    private Long id;

    private String email;

    private String nickname;

    private Role authority;

    private String accessToken;

    private String refreshToken;

}
