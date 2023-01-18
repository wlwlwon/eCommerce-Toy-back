package com.ecommerce.ecommerce.domain.cart.service;

import com.ecommerce.ecommerce.domain.cart.repository.CartRepository;
import com.ecommerce.ecommerce.domain.cart.domain.Cart;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.SaveToCartRequest;
import com.ecommerce.ecommerce.domain.product.service.ProductService;
import com.ecommerce.ecommerce.domain.stuff.domain.Stuff;
import com.ecommerce.ecommerce.domain.stuff.repository.StuffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        List<Stuff> stuffList = cart.getStuff();
        Stuff stuff = stuffRepository.findByCartAndProduct(cart, product).get();

        if(stuff == null){
            stuff = makeStuff(dto, product);
        }else{
            stuff.setProductNum(dto.getProductNum()+stuff.getProductNum());
        }

        stuffList.add(stuff);
        cart.setStuff(stuffList);

        stuffRepository.save(stuff);
        cartRepository.save(cart);

    }

    private Stuff makeStuff(SaveToCartRequest dto, Product product) {
        return Stuff.builder()
                .productNum(dto.getProductNum())
                .product(product)
                .build();
    }

}
