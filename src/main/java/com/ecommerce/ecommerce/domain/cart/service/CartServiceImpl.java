package com.ecommerce.ecommerce.domain.cart.service;

import com.ecommerce.ecommerce.domain.cart.repository.CartRepository;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.repository.MemberRepository;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.product.service.ProductService;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import com.ecommerce.ecommerce.domain.stuff.repository.StuffRepository;
import com.ecommerce.ecommerce.domain.stuff.service.StuffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final StuffRepository stuffRepository;
    private final StuffService stuffService;
    private final MemberRepository memberRepository;

    @Override
    public List<Stuff> getCart(Member loginMember) {
        Cart cart = findOrCreateNewCart(loginMember);
        return cart.getStuffList().stream()
                .map((cartStuff) -> toStuffResponse(cartStuff))
                .collect(Collectors.toList());
    }

    public Stuff toStuffResponse(Stuff stuff){
        return Stuff.builder()
                .product(stuff.getProduct())
                .productNum(stuff.getProductNum())
                .build();
    }
    @Transactional
    public void saveProduct(Member loginMember, SaveToCartRequest dto){
        if(!productService.checkIsProductExist(dto.getProductId()))
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        if(dto.getProductNum() == 0)
            throw new IllegalArgumentException("0개의 상품은 장바구니에 담을 수 없습니다.");

        Cart cart = findOrCreateNewCart(loginMember);
        Product product = productService.getProduct(dto.getProductId());

        List<Stuff> stuffList = cart.getStuffList();
        Stuff stuff = Stuff.builder().product(product).productNum(dto.getProductNum()).build();

        addStuffToCart(dto, cart, product, stuffList, stuff);

        cartRepository.save(cart);
    }

    private Stuff addStuffToCart(SaveToCartRequest dto, Cart cart, Product product, List<Stuff> stuffList, Stuff stuff) {

        if(stuff == null){
            stuff = stuffService.makeStuff(dto, product);
        }else{
            stuff.setProductNum(dto.getProductNum()+ stuff.getProductNum());
        }

        stuffList.add(stuff);
        cart.setStuffList(stuffList);

        return stuffRepository.save(stuff);
    }

    @Override
    public Cart findOrCreateNewCart(Member member) {
        Optional<Cart> optionalCart = cartRepository.findByMember(member);
        Cart cart;
        if(optionalCart.isEmpty()){
            List<Stuff> stuffList = new ArrayList<>();
            cart = cartRepository.save(Cart.builder().stuffList(stuffList).member(member).build());
            member.setCart(cart);
            memberRepository.save(member);
        }else{
            cart = optionalCart.get();
        }

        return cart;
    }

}
