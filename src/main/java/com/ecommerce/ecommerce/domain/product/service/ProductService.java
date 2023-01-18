package com.ecommerce.ecommerce.domain.product.service;

import com.ecommerce.ecommerce.config.UserPrincipal;
import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.ProductCreateDTO;
import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.ecommerce.ecommerce.domain.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getProducts(ProductsRequest dto);

    List<ProductResponse> searchProductsByName(String name) throws Exception;

    Product getProduct(long id);

    boolean checkIsProductExist(long id);

    ProductResponse createProduct(Member member, ProductCreateDTO dto);
}
