package com.ecommerce.ecommerce.domain.member.controller;


import com.ecommerce.ecommerce.config.authentication.AuthenticationService;
import com.ecommerce.ecommerce.config.authentication.dto.RegenerateTokenDto;
import com.ecommerce.ecommerce.config.authentication.dto.TokenDto;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signUp(@RequestBody MemberRequestDTO memberRequestDTO) {
        boolean isDuplicated = memberService.isDuplicatedEmail(memberRequestDTO.getEmail());
        if (isDuplicated)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        MemberResponseDTO memberResponseDTO = memberService.signUp(memberRequestDTO);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<MemberResponseDTO> signIn(@RequestBody MemberRequestDTO memberRequestDTO) throws Exception {
        MemberResponseDTO memberResponseDTO = authenticationService.signInAndReturnJWT(memberRequestDTO);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@RequestBody RegenerateTokenDto refreshTokenDto) {
        return authenticationService.regenerateToken(refreshTokenDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<TokenDto> logout(@RequestBody TokenDto tokenDto){
        return authenticationService.logout(tokenDto);
    }

}
