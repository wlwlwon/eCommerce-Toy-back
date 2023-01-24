package com.ecommerce.ecommerce.config.jwt;


import com.ecommerce.ecommerce.config.exception.CustomException;
import com.ecommerce.ecommerce.config.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = jwtProvider.getAuthentication(request);
        if (authentication != null && jwtProvider.isAccessTokenValid(request)) {
            String isLogout = redisTemplate.opsForValue().get(SecurityUtils.extractAuthTokenFromRequest(request));
            if(ObjectUtils.isEmpty(isLogout))
                SecurityContextHolder.getContext().setAuthentication(authentication);
            else
                throw new CustomException("사용할 수 없는 token입니다.", HttpStatus.BAD_REQUEST);
        }

        filterChain.doFilter(request, response);
    }

}
