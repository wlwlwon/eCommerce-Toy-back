package com.ecommerce.ecommerce.domain.friend;

import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;

public interface FriendService {

    MemberResponseDTO addFriend(String currentMember, FriendRequestDTO friendRequestDTO) throws Exception;
}
