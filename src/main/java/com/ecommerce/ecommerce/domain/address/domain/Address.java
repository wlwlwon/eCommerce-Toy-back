package com.ecommerce.ecommerce.domain.address.domain;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    private long id;

    private boolean isMain;

    private String name;

    private String content;

}
