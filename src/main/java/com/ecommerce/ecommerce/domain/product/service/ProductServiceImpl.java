package com.ecommerce.ecommerce.domain.product.service;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.repository.ProductRepository;
import com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum;
import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.ecommerce.ecommerce.domain.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts(ProductsRequest dto){
        DeliveryTypeEnum deliveryType = dto.getDeliveryType();

        boolean isRocket = dto.isRocket();
        int listSize = dto.getListSize();
        int startId = dto.getStart();

        List<Product> products = getProductsSortByDeliveryType(deliveryType, isRocket, listSize, startId);

        return products.stream()
                .map(ProductResponse::toResponse)
                .collect(Collectors.toList());
    }

    private List<Product> getProductsSortByDeliveryType (DeliveryTypeEnum deliveryType, boolean isRocket, int listSize, int startId) {
        List<Product> products = new ArrayList<>();

        //pageable 적용
        switch (deliveryType) {
            case ROCKET:
                products = productRepository.findProductByRocket(true);
                break;
            case ROCKET_FRESH:
                if (isRocket) {
                    products = productRepository.findProductByRocketAndRocketFresh(true, true);
                } else {
                    products = productRepository.findProductByRocketFresh(true);
                }
                break;
            case ROCKET_GLOBAL:
                if (isRocket) {
                    products = productRepository.findProductByRocketAndRocketGlobal(true, true);
                } else {
                    products = productRepository.findProductByRocketGlobal(true);
                }
                break;
        }
        return products;
    }
    public List<ProductResponse> searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByNameLike(keyword);
        return ProductResponse.toList(products);
    }

    public Product getProduct(long id){
        return productRepository.findById(id).get();
    }
    public boolean checkIsProductExist(long id){
        return productRepository.findById(id).isPresent();
    }


}
