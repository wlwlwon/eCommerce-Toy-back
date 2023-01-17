package com.ecommerce.ecommerce.domain.cart.service;

import com.ecommerce.ecommerce.domain.cart.CartRepository;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.product.service.ProductService;
import com.ecommerce.ecommerce.domain.stuff.Stuff;
import com.ecommerce.ecommerce.domain.stuff.StuffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final StuffRepository stuffRepository;

    @Transactional
    public void saveProduct(Member member, SaveToCartRequest dto){
        if(!productService.checkIsProductExist(dto.getProductId()))
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        if(dto.getProductNum() == 0)
            throw new IllegalArgumentException("0개의 상품은 장바구니에 담을 수 없습니다.");
        Cart cart = member.getCart();
        Product product = productService.getProduct(dto.getProductId());

        Stuff stuff = stuffRepository.findByCartAndProduct(cart, product).get();
        if(stuff == null){
            stuff = makeStuff(dto, product);
            stuffRepository.save(stuff);
        }else{
            cartRepository.updateProductNum(stuff.getId(),dto.getProductNum()+stuff.getProductNum());
        }

    }

    private Stuff makeStuff(SaveToCartRequest dto, Product product) {
        return Stuff.builder()
                .productNum(dto.getProductNum())
                .product(product)
                .build();
    }

    public boolean checkIsStuffExistInCart(Stuff stuff,Member member){
        return cartRepository.findByMemberAndStuff(member, stuff).isPresent();
    }

    public int getSavedStuffNum(Cart cart,Stuff stuff){
        return cartRepository.getByStuff(stuff).getProductNum();
    }
}
