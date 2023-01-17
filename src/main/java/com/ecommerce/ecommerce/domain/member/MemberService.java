package com.ecommerce.ecommerce.domain.member;

import com.ecommerce.ecommerce.domain.friend.FriendRequestDTO;

public interface MemberService {

    MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO);

    boolean isDuplicatedEmail(String email);

    Member getMember(String userName);

}
