package com.ecommerce.ecommerce.config.jwt;

import com.ecommerce.ecommerce.config.UserPrincipal;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;


public interface JwtProvider {

    String generateAccessOrRefreshToken(UserPrincipal auth, JwtType jwtType);

    Authentication getAuthentication(HttpServletRequest request);

    Authentication getAccessAuthentication(String token);

    Authentication getRefreshAuthentication(String token);

    boolean isAccessTokenValid(HttpServletRequest request);

    boolean isAccessTokenValid(String token);

    boolean isRefreshTokenValid(String token);

    Long getExpiration(String token);

}
