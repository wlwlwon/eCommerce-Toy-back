package com.ecommerce.ecommerce.config.authentication;

import com.ecommerce.ecommerce.domain.member.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.MemberResponseDTO;

public interface AuthenticationService {

    MemberResponseDTO signInAndReturnJWT(MemberRequestDTO signInRequest);
}
