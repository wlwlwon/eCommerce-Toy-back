package com.ecommerce.ecommerce.domain.address.domain;

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
