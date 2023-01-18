package com.ecommerce.ecommerce.domain.friend;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/addFriend")
    public ResponseEntity<MemberResponseDTO> addFriend(@AuthenticationPrincipal UserPrincipal member, @RequestBody FriendRequestDTO friendRequestDTO) throws Exception{
        MemberResponseDTO memberResponseDTO = friendService.addFriend(member.getUsername(),friendRequestDTO);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.OK);
    }

}
