package com.ecommerce.ecommerce.domain.payment.service;

import com.ecommerce.ecommerce.domain.payment.domain.Payment;
import com.ecommerce.ecommerce.domain.payment.dto.PaymentDTO;

public interface PaymentService {

    void pay(PaymentDTO paymentDto);
    long savePaymentInfo(Payment payment);

    boolean mockPayment(PaymentDTO dto);
}
