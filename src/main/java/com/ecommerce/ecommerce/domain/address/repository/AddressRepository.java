package com.ecommerce.ecommerce.domain.address.repository;

import com.ecommerce.ecommerce.domain.address.domain.Address;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
