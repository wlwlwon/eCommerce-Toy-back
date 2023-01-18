package com.ecommerce.ecommerce.domain.payment.service;

import com.ecommerce.ecommerce.domain.payment.repository.PaymentRepository;
import com.ecommerce.ecommerce.domain.payment.domain.Payment;
import com.ecommerce.ecommerce.domain.payment.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    public void pay(PaymentDTO paymentDto){
        if(!mockPayment(paymentDto))
            throw new IllegalArgumentException("결제에 실패하였습니다.");
    }

    public long savePaymentInfo(Payment payment){
        paymentRepository.save(payment);
        return payment.getId();
    }

    public boolean mockPayment(PaymentDTO dto){
        return true;
    }

}
