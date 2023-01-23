package com.ecommerce.ecommerce.domain.address.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {

    private boolean isMain;

    private String name;

    private String content;
}
