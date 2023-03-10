package com.ecommerce.ecommerce.config.authentication;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.config.authentication.dto.RegenerateTokenDto;
import com.ecommerce.ecommerce.config.authentication.dto.TokenDto;
import com.ecommerce.ecommerce.config.exception.CustomException;
import com.ecommerce.ecommerce.config.jwt.JwtProvider;
import com.ecommerce.ecommerce.config.jwt.JwtType;
import com.ecommerce.ecommerce.config.utils.SecurityUtils;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ModelMapper modelMapper;

    @Value("${app.jwt.Refresh-expiration-in-ms}")
    private Long JWT_Refresh_EXPIRATION_IN_MS;

    @Override
    public MemberResponseDTO signInAndReturnJWT(MemberRequestDTO signInRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
            );

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            String Access_jwt = jwtProvider.generateAccessOrRefreshToken(userPrincipal, JwtType.Access);
            String Refresh_jwt = jwtProvider.generateAccessOrRefreshToken(userPrincipal, JwtType.Refresh);

            Member signInUser = userPrincipal.getMember();
            signInUser.setAccessToken(Access_jwt);
            signInUser.setRefreshToken(Refresh_jwt);
            memberRepository.save(signInUser);

            redisTemplate.opsForValue().set(
                    signInUser.getEmail(),
                    Refresh_jwt,
                    JWT_Refresh_EXPIRATION_IN_MS,
                    TimeUnit.MILLISECONDS
            );

            System.out.println("***** accessToken : " + Access_jwt);

            return modelMapper.map(signInUser, MemberResponseDTO.class);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid credentials supplied", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<TokenDto> regenerateToken(@Valid RegenerateTokenDto refreshTokenDto) {
        String refresh_token = refreshTokenDto.getRefreshToken();
        try {
            // Refresh Token ??????
            if (!jwtProvider.isRefreshTokenValid(refresh_token)) {
                throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
            }

            // Access Token ?????? User email??? ????????????.
            Authentication authentication = jwtProvider.getRefreshAuthentication(refresh_token);

            // Redis?????? ????????? Refresh Token ?????? ????????????.
            String redis_refreshToken = redisTemplate.opsForValue().get(authentication.getName());
            if (!redis_refreshToken.equals(refresh_token)) {
                throw new CustomException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
            }

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // ?????? ?????????
            String new_refresh_token = jwtProvider.generateAccessOrRefreshToken(userPrincipal, JwtType.Refresh);
            String new_access_token = jwtProvider.generateAccessOrRefreshToken(userPrincipal, JwtType.Access);

            TokenDto tokenDto = getTokenDto(new_refresh_token, new_access_token);

            // RefreshToken Redis??? ????????????
            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    new_refresh_token,
                    JWT_Refresh_EXPIRATION_IN_MS,
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
        }
    }

    private TokenDto getTokenDto(String new_refresh_token, String new_access_token) {
        return TokenDto.builder()
                .accessToken(new_access_token)
                .refreshToken(new_refresh_token)
                .build();
    }

    @Override
    public ResponseEntity<TokenDto> logout(TokenDto tokenDto) {

        String access_token = tokenDto.getAccessToken();
        try {
            if (!jwtProvider.isAccessTokenValid(access_token)) {
                throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = jwtProvider.getAccessAuthentication(access_token);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            //refresh?????? ????????? ?????? ??????
            if (redisTemplate.opsForValue().get(userPrincipal.getUsername()) != null) {
                redisTemplate.delete(userPrincipal.getUsername());
            }

            //access token??? ???????????? ????????? blacklist??? ??????
            Long expiration = jwtProvider.getExpiration(access_token);
            redisTemplate.opsForValue().set(
                    access_token,
                    "logout",
                    expiration,
                    TimeUnit.MILLISECONDS
            );
            HttpHeaders httpHeaders = new HttpHeaders();
            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("logout fail", HttpStatus.BAD_REQUEST);
        }
    }
}
