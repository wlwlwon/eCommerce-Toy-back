package com.ecommerce.ecommerce.domain.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SuccessResponse {

    private StatusEnum status;
    private String message;
    private Object data;

}
