package com.ecommerce.ecommerce.domain.member;

import com.ecommerce.ecommerce.domain.member.friend.FriendRequestDTO;

public interface MemberService {

    MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO);

    MemberResponseDTO addFriend(String currentMember, FriendRequestDTO friendRequestDTO) throws Exception ;

    boolean isDuplicatedEmail(String email);

}
