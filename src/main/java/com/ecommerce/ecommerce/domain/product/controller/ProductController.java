package com.ecommerce.ecommerce.domain.product.controller;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.global.common.StatusEnum;
import com.ecommerce.ecommerce.domain.global.common.SuccessResponse;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.member.service.MemberService;
import com.ecommerce.ecommerce.domain.product.dto.ProductCreateDTO;
import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.ecommerce.ecommerce.domain.product.dto.ProductResponse;
import com.ecommerce.ecommerce.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/get")
    public SuccessResponse getProducts(@Valid @RequestBody ProductsRequest dto) {
        List<ProductResponse> products = productService.getProducts(dto);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("상품 목록 Get OK")
                .data(products)
                .build();
    }

    @GetMapping("/search")
    public SuccessResponse searchProductsByKeyword(@RequestParam(value = "name", required = false) String name) throws Exception{
        List<ProductResponse> products = productService.searchProductsByName(name);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("상품 Search OK")
                .data(products)
                .build();
    }

    @PostMapping("/create")
    public SuccessResponse createProduct(@AuthenticationPrincipal UserPrincipal member,@RequestBody ProductCreateDTO dto) {
        Member getMember = memberService.getMember(member.getUsername());
        ProductResponse product = productService.createProduct(getMember, dto);
        return SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("상품 Create OK")
                .data(product)
                .build();
    }

}
