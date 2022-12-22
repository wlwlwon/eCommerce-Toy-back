package com.ecommerce.ecommerce.domain.member;

import jakarta.persistence.*;
import lombok.*;

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

    @Transient
    private String accessToken;

    @Column(length = 1000)
    private String refreshToken;

}
