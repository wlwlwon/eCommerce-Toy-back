package com.ecommerce.ecommerce.config.authentication.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class RegenerateTokenDto {
    @NotBlank
    private String refreshToken;
}
