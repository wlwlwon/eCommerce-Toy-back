package com.ecommerce.ecommerce.domain.address.service;

import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.address.dto.AddressRequestDTO;
import com.ecommerce.ecommerce.domain.address.dto.AddressResponseDTO;
import com.ecommerce.ecommerce.domain.address.repository.AddressRepository;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AddressResponseDTO addAddress(Member member, AddressRequestDTO addressRequestDTO) {
        Address address = getAddress(addressRequestDTO);
        member.setAddress(address);
        return modelMapper.map(addressRepository.save(address), AddressResponseDTO.class);
    }

    private Address getAddress(AddressRequestDTO addressRequestDTO) {
        return Address.builder()
                .isMain(addressRequestDTO.isMain())
                .name(addressRequestDTO.getName())
                .content(addressRequestDTO.getContent())
                .build();
    }
}
