package com.ecommerce.ecommerce.domain.product.service;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.ecommerce.ecommerce.domain.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getProducts(ProductsRequest dto);

    List<ProductResponse> searchProductsByKeyword(String keyword);

    Product getProduct(long id);

    boolean checkIsProductExist(long id);
}
