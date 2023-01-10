package com.ecommerce.ecommerce.domain.member;

import com.ecommerce.ecommerce.domain.member.friend.Friend;
import com.ecommerce.ecommerce.domain.member.friend.FriendRequestDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    @Override
    public MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO)  {

        Member member = modelMapper.map(memberRequestDTO, Member.class);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEmail(member.getEmail());
        member.setAuthority(Role.Commerce_USER);

        return modelMapper.map(memberRepository.save(member), MemberResponseDTO.class);
    }

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
        MemberResponseDTO memberResponseDTO = modelMapper.map(member, MemberResponseDTO.class);
        return memberResponseDTO;
    }

}
