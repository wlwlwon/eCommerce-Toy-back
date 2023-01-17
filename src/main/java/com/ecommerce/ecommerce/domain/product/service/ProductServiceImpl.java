package com.ecommerce.ecommerce.domain.product.service;

import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.ProductRepository;
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

        switch (deliveryType) {
            case ROCKET:
                products = productRepository.getProductsByIsRocket(true, startId, listSize);
                break;
            case ROCKET_FRESH:
                if (isRocket) {
                    products = productRepository.getProductsByIsRocketAndIsRocketFresh(true, true, startId, listSize);
                } else {
                    products = productRepository.getProductsByIsRocketFresh(true, startId, listSize);
                }
                break;
            case ROCKET_GLOBAL:
                if (isRocket) {
                    products = productRepository.getProductsByIsRocketAndIsRocketGlobal(true, true, startId, listSize);
                } else {
                    products = productRepository.getProductsByIsRocketGlobal(true, startId, listSize);
                }
                break;
        }
        return products;
    }
    public List<ProductResponse> searchProductsByKeyword(String keyword) {
        List<Product> products = productRepository.getProductsByKeyword(keyword);
        return ProductResponse.toList(products);
    }

    public Product getProduct(long id){
        return productRepository.findByProductId(id).get();
    }
    public boolean checkIsProductExist(long id){
        return productRepository.findByProductId(id).isPresent();
    }


}
