package com.ecommerce.ecommerce.domain.order.service;

import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.cart.repository.CartRepository;
import com.ecommerce.ecommerce.domain.cart.service.CartService;
import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.service.CouponService;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.order.domain.OrderPurchase;
import com.ecommerce.ecommerce.domain.order.dto.OrderPurchaseResponseDTO;
import com.ecommerce.ecommerce.domain.order.dto.OrderRequestDTO;
import com.ecommerce.ecommerce.domain.order.repository.OrderRepository;
import com.ecommerce.ecommerce.domain.payment.domain.Payment;
import com.ecommerce.ecommerce.domain.payment.dto.PaymentDTO;
import com.ecommerce.ecommerce.domain.payment.service.PaymentService;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final CartRepository cartRepository;
    private final CouponService couponService;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    public void order(Member member, OrderRequestDTO orderRequestDto){

        Optional<Cart> cart = cartRepository.findByMember(member);
        Optional<Coupon> coupon = couponService.findCoupon(orderRequestDto.getCouponId());

        long totalPrice = getTotalProductPrice(cart.get()) + getTotalDeliveryFee(cart.get());
        if(totalPrice == 0)
            throw new IllegalArgumentException("주문을 요청한 상품이 없습니다.");

        long discountPrice = couponService.getDiscountPriceByCoupon(member, coupon, cart.get(), getTotalProductPrice(cart.get()));

        PaymentDTO paymentDto = PaymentDTO.builder()
                .type(orderRequestDto.getType())
                .totalPrice(totalPrice - discountPrice)
                .build();

        try {
            paymentService.pay(paymentDto);
        }catch(Exception e) {
            throw new IllegalArgumentException("결제에 실패하였습니다.", e);
        }

        Payment payment = Payment.builder()
                .type(paymentDto.getType().ordinal())
                .totalPrice(paymentDto.getTotalPrice())
                .status(true)
                .discountPrice(discountPrice)
                .build();

        long paymentId = paymentService.savePaymentInfo(payment);

        couponService.increaseUseCount(member,coupon);

        saveOrderInfo(member, paymentId, orderRequestDto,cart.get());
        deleteCartProducts(member, cart.get());
    }

    @Override
    public List<OrderPurchaseResponseDTO> getOrderList(Member member) {
        List<OrderPurchase> order = member.getOrder();
        List<OrderPurchaseResponseDTO> collect = order.stream()
                .map(orderPurchase -> modelMapper.map(orderPurchase, OrderPurchaseResponseDTO.class))
                .collect(Collectors.toList());
        return collect;
    }

    public long getTotalProductPrice(Cart cart){
        return cart.getStuffList().stream().mapToLong(cartStuff -> cartStuff.getProduct().getPrice() * cartStuff.getProductNum()).sum();
    }

    public long getTotalDeliveryFee(Cart cart){
        return cart.getStuffList().stream().mapToLong(cartStuff -> cartStuff.getProduct().getDeliveryFee()).sum();
    }

    public void saveOrderInfo(Member member, long paymentId, OrderRequestDTO dto, Cart cart){
        //주소 검사
        String receiverAddress = member.getAddress().getContent();

        List<Product> productList = cart.getStuffList().stream()
                .map(stuff -> stuff.getProduct())
                .collect(Collectors.toList());

        OrderPurchase order = OrderPurchase.builder()
                .member(member)
                .consumerName(member.getName())
                .productList(productList)
                .consumerPhone(member.getPhoneNumber())
                .receiverName(dto.getReceiverName())
                .receiverAddress(receiverAddress)
                .receiverPhone(dto.getReceiverPhone())
                .status(false)
                .paymentId(paymentId)
                .receiverRequest(dto.getReceiverRequest())
                .build();

        member.getOrder().add(order);
        orderRepository.save(order);
    }

    public void deleteCartProducts(Member member,Cart cart){
        member.setCart(null);
        cartRepository.delete(cart);
        cartService.findOrCreateNewCart(member);
    }

}
