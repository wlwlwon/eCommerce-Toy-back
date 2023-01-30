package com.ecommerce.ecommerce.config.authentication;

import com.ecommerce.ecommerce.config.authentication.dto.RegenerateTokenDto;
import com.ecommerce.ecommerce.config.authentication.dto.TokenDto;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public interface AuthenticationService {

    MemberResponseDTO signInAndReturnJWT(MemberRequestDTO signInRequest);

    ResponseEntity<TokenDto> regenerateToken(@Valid RegenerateTokenDto refreshTokenDto);

    ResponseEntity<TokenDto> logout(TokenDto tokenDto);
}
