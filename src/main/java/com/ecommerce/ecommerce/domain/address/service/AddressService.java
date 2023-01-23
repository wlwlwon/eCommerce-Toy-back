package com.ecommerce.ecommerce.domain.address.service;

import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.address.dto.AddressRequestDTO;
import com.ecommerce.ecommerce.domain.member.domain.Member;

public interface AddressService {
    Address addAddress(Member member, AddressRequestDTO addressRequestDTO);
}
