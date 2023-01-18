package com.ecommerce.ecommerce.config.jwt;

import com.ecommerce.ecommerce.config.UserPrincipal;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


public interface JwtProvider {

    String generateAccessToken(UserPrincipal auth);

    String generateNewAccessToken(Member member);

    String generateRefreshToken(UserPrincipal auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isAccessTokenValid(HttpServletRequest request);

}
