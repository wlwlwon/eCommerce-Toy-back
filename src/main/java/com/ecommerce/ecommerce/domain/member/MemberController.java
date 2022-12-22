package com.ecommerce.ecommerce.domain.member;


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

    @PostMapping("/signUp")
    public ResponseEntity<MemberResponseDTO> signUp(@RequestBody MemberRequestDTO memberRequestDTO) {

        boolean isDuplicated = memberService.isDuplicatedEmail(memberRequestDTO.getEmail());
        if (isDuplicated)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        MemberResponseDTO memberResponseDTO = memberService.signUp(memberRequestDTO);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.CREATED);
    }

}
