package com.ecommerce.ecommerce.domain.member;


import com.ecommerce.ecommerce.domain.member.friend.Friend;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role authority;

    @Transient
    private String accessToken;

    @Column(length = 1000)
    private String refreshToken;

    @OneToMany(mappedBy = "member")
    private Set<Friend> friendSet = new HashSet<>();

}
