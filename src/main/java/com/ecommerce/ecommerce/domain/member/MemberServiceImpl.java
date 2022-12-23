package com.ecommerce.ecommerce.domain.member;

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

}
