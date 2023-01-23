package com.ecommerce.ecommerce.domain.address.service;

import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.address.dto.AddressRequestDTO;
import com.ecommerce.ecommerce.domain.address.repository.AddressRepository;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    @Override
    public Address addAddress(Member member, AddressRequestDTO addressRequestDTO) {

        Address address = Address.builder()
                .isMain(addressRequestDTO.isMain())
                .name(addressRequestDTO.getName())
                .content(addressRequestDTO.getContent())
                .build();

        member.setAddress(address);
        Address save = addressRepository.save(address);
        memberRepository.save(member);

        return save;
    }
}
