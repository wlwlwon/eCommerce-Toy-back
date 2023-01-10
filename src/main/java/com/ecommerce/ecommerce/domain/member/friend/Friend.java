package com.ecommerce.ecommerce.domain.member.friend;

import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.member.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role authority;

    @ManyToOne
    private Member member;
}
