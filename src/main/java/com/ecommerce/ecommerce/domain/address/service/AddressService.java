package com.ecommerce.ecommerce.domain.address.service;

import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.address.dto.AddressRequestDTO;
import com.ecommerce.ecommerce.domain.address.dto.AddressResponseDTO;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import org.springframework.transaction.annotation.Transactional;

public interface AddressService {
    @Transactional
    AddressResponseDTO addAddress(Member member, AddressRequestDTO addressRequestDTO);
}
