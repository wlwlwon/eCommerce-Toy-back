package com.ecommerce.ecommerce.config.authentication;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.config.jwt.JwtProvider;
import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.member.MemberRepository;
import com.ecommerce.ecommerce.domain.member.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    @Override
    public MemberResponseDTO signInAndReturnJWT(MemberRequestDTO signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String Access_jwt = jwtProvider.generateAccessToken(userPrincipal);
        String Refresh_jwt = jwtProvider.generateRefreshToken(userPrincipal);

        Member signInUser = userPrincipal.getMember();
        signInUser.setAccessToken(Access_jwt);
        signInUser.setRefreshToken(Refresh_jwt);
        memberRepository.save(signInUser);

        System.out.println("***** accessToken : " + Access_jwt);

        return modelMapper.map(signInUser, MemberResponseDTO.class);
    }
}
