package com.ecommerce.ecommerce.config.authentication;

import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;

public interface AuthenticationService {

    MemberResponseDTO signInAndReturnJWT(MemberRequestDTO signInRequest);
}
