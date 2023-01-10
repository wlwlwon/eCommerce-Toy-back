package com.ecommerce.ecommerce.config.jwt;

import com.ecommerce.ecommerce.config.UserPrincipal;

import com.ecommerce.ecommerce.config.utils.SecurityUtils;
import com.ecommerce.ecommerce.domain.member.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret-Access}")
    private String JWT_Access_SECRET;

    @Value("${app.jwt.secret-Refresh}")
    private String JWT_Refresh_SECRET;

    @Value("${app.jwt.Access-expiration-in-ms}")
    private Long JWT_Access_EXPIRATION_IN_MS;

    @Value("${app.jwt.Refresh-expiration-in-ms}")
    private Long JWT_Refresh_EXPIRATION_IN_MS;


    @Override
    public String generateAccessToken(UserPrincipal auth) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_Access_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getEmail())
                .claim("userName", auth.getUsername())
                .claim("roles", authorities)
                .claim("userEmail", auth.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_Access_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateNewAccessToken(Member member) {
        Key key = Keys.hmacShaKeyFor(JWT_Access_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("userName", member.getNickname())
                .claim("roles", member.getAuthority())
                .claim("userEmail", member.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_Access_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserPrincipal auth) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_Refresh_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getEmail())
                .claim("userName", auth.getUsername())
                .claim("roles", authorities)
                .claim("userEmail", auth.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_Refresh_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractAccessClaims(request);
        if (claims == null) {
            return null;
        }

        String username = claims.getSubject();

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrincipal.builder()
                .username(username)
                .authorities(authorities)
                .build();

        if (username == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isAccessTokenValid(HttpServletRequest request) {
        Claims claims = extractAccessClaims(request);
        if (claims == null) {
            return false;
        }

        if (claims.getExpiration().before(new Date())) {
            return false;
        }

        return true;
    }

    private Claims extractAccessClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);
        if (token == null) {
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_Access_SECRET.getBytes(StandardCharsets.UTF_8));

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new InvalidParameterException("유효하지 않은 토큰입니다");
        }
    }

}
