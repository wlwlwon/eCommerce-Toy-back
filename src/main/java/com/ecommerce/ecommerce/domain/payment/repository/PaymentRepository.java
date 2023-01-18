package com.ecommerce.ecommerce.domain.payment.repository;

import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
