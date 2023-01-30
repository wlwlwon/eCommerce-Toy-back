package com.ecommerce.ecommerce.domain.cart.service;

import com.ecommerce.ecommerce.config.exception.CustomException;
import com.ecommerce.ecommerce.domain.cart.repository.CartRepository;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.product.service.ProductService;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import com.ecommerce.ecommerce.domain.stuff.dto.StuffResponseDto;
import com.ecommerce.ecommerce.domain.stuff.repository.StuffRepository;
import com.ecommerce.ecommerce.domain.stuff.service.StuffService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final StuffRepository stuffRepository;
    private final StuffService stuffService;
    private final ModelMapper modelMapper;

    @Override
    public List<StuffResponseDto> getCart(Member loginMember) {
        Cart cart = findOrCreateNewCart(loginMember);
        return cart.getStuffList().stream()
                .map((cartStuff) -> modelMapper.map(cartStuff, StuffResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveProduct(Member loginMember, SaveToCartRequest dto) {
        if (!productService.checkIsProductExist(dto.getProductId()))
            throw new CustomException("존재하지 않는 상품입니다.", HttpStatus.BAD_REQUEST);
        if (dto.getProductNum() == 0)
            throw new CustomException("0개의 상품은 장바구니에 담을 수 없습니다.", HttpStatus.BAD_REQUEST);

        Cart cart = findOrCreateNewCart(loginMember);
        List<Stuff> stuffList = cart.getStuffList();

        Product product = productService.getProductById(dto.getProductId());
        Stuff stuff = stuffService.makeStuff(dto, product);

        addStuffToCart(cart, stuffList, stuff);
        cartRepository.save(cart);
    }

    private Stuff addStuffToCart(Cart cart, List<Stuff> stuffList, Stuff stuff) {
        stuffList.add(stuff);
        cart.setStuffList(stuffList);
        return stuffRepository.save(stuff);
    }

    @Override
    public Cart findOrCreateNewCart(Member member) {
        Optional<Cart> optionalCart = cartRepository.findByMember(member);
        Cart cart;
        if (optionalCart.isEmpty()) {
            List<Stuff> stuffList = new ArrayList<>();
            cart = cartRepository.save(getCart(member, stuffList));
            member.setCart(cart);
        } else {
            cart = optionalCart.get();
        }
        return cart;
    }

    private Cart getCart(Member member, List<Stuff> stuffList) {
        return Cart.builder()
                .stuffList(stuffList)
                .member(member)
                .build();
    }
}
