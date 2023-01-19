package com.ecommerce.ecommerce.domain.order.service;

import com.ecommerce.ecommerce.domain.address.repository.AddressRepository;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.cart.repository.CartRepository;
import com.ecommerce.ecommerce.domain.coupon.domain.Coupon;
import com.ecommerce.ecommerce.domain.coupon.service.CouponService;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.order.domain.OrderPurchase;
import com.ecommerce.ecommerce.domain.order.domain.OrderProduct;
import com.ecommerce.ecommerce.domain.order.dto.OrderRequestDTO;
import com.ecommerce.ecommerce.domain.order.repository.OrderProductRepository;
import com.ecommerce.ecommerce.domain.order.repository.OrderRepository;
import com.ecommerce.ecommerce.domain.payment.domain.Payment;
import com.ecommerce.ecommerce.domain.payment.dto.PaymentDTO;
import com.ecommerce.ecommerce.domain.payment.service.PaymentService;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import com.ecommerce.ecommerce.domain.stuff.repository.StuffRepository;
import lombok.RequiredArgsConstructor;
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
    private final AddressRepository addressRepository;
    private final OrderProductRepository orderProductRepository;
    private final StuffRepository stuffRepository;

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
        couponService.increaseUseCount(member,coupon.get());

        OrderPurchase order = saveOrderInfo(member, paymentId, orderRequestDto);

        List<OrderProduct> orderProducts = getOrderProduct(order, cart.get());
        saveOrderProducts(orderProducts);

        deleteCartProducts(cart.get());
    }

    public List<OrderProduct> getOrderProduct(OrderPurchase order, Cart cart){
        return cart.getStuffList().stream()
                .map((cartStuff) -> toOrderProductResponse(order, cartStuff))
                .collect(Collectors.toList());
    }

    public OrderProduct toOrderProductResponse(OrderPurchase order, Stuff stuff){
        return OrderProduct.builder()
//                .order(order)
//                .stuff(stuff)
                .productId(stuff.getProduct().getId())
                .productNum(stuff.getProductNum())
                .build();
    }

    public long getTotalProductPrice(Cart cart){
        return cart.getStuffList().stream().mapToLong(cartStuff -> cartStuff.getProduct().getPrice() * cartStuff.getProductNum()).sum();
    }

    public long getTotalDeliveryFee(Cart cart){
        return cart.getStuffList().stream().mapToLong(cartStuff -> cartStuff.getProduct().getDeliveryFee()).sum();
    }

    public OrderPurchase saveOrderInfo(Member member, long paymentId, OrderRequestDTO dto){

        String receiverAddress = addressRepository.findAddressByMember(member).get().getContent();

        OrderPurchase order = OrderPurchase.builder()
                .member(member)
                .consumerName(member.getName())
                .consumerPhone(member.getPhoneNumber())
                .receiverName(dto.getReceiverName())
                .receiverAddress(receiverAddress)
                .receiverPhone(dto.getReceiverPhone())
                .status(false)
                .paymentId(paymentId)
                .receiverRequest(dto.getReceiverRequest())
                .build();

        orderRepository.save(order);
        return order;
    }

    public void saveOrderProducts(List<OrderProduct> orderProducts){
        orderProducts.stream()
                .forEach(orderProduct -> orderProductRepository.save(orderProduct));
    }

    public void deleteCartProducts(Cart cart){
        cart.getStuffList().stream()
                .forEach(stuff -> stuffRepository.delete(stuff));
    }

}
