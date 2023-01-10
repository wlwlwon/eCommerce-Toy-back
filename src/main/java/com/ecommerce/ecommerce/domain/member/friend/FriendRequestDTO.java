package com.ecommerce.ecommerce.domain.member.friend;

import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
public class FriendRequestDTO {

    private Long id;

    private String email;

    private String nickname;

    private Role authority;

    private Member member;
}
