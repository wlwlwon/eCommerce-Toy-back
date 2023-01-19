package com.ecommerce.ecommerce.domain.member.controller;


import com.ecommerce.ecommerce.config.authentication.AuthenticationService;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MemberResponseDTO> signIn(@RequestBody MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO memberResponseDTO = authenticationService.signInAndReturnJWT(memberRequestDTO);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }

}
