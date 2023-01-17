package com.ecommerce.ecommerce.domain.friend;

import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.member.MemberRepository;
import com.ecommerce.ecommerce.domain.member.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public MemberResponseDTO addFriend(String currentMember, FriendRequestDTO friendRequestDTO) throws Exception{
        Optional<Member> byEmail = memberRepository.findByEmail(currentMember);

        if(byEmail.isEmpty()){
            throw new Exception("멤버가 존재하지 않습니다.");
        }

        Member member = byEmail.get();
        Friend friend = modelMapper.map(friendRequestDTO, Friend.class);

        member.getFriendSet().add(friend);
        memberRepository.save(member);

        return modelMapper.map(member, MemberResponseDTO.class);
    }


}
