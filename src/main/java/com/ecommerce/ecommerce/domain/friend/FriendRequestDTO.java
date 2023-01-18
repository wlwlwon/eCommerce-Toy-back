package com.ecommerce.ecommerce.domain.friend;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FriendRequestDTO {

    private Long id;

    private String email;

    private String nickname;

    private Role authority;

    private Member member;
}
