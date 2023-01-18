package com.ecommerce.ecommerce.domain.member.service;

import com.ecommerce.ecommerce.domain.member.Role;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.dto.MemberRequestDTO;
import com.ecommerce.ecommerce.domain.member.dto.MemberResponseDTO;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        memberInfo(member);

        return modelMapper.map(memberRepository.save(member), MemberResponseDTO.class);
    }

    private void memberInfo(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setEmail(member.getEmail());
        member.setAuthority(Role.Commerce_USER);
    }

    @Override
    public Member getMember(String userName){
        return memberRepository.findByEmail(userName).get();
    }



}
