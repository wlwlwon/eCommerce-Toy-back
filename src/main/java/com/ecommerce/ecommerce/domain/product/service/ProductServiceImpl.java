package com.ecommerce.ecommerce.domain.product.service;

import com.ecommerce.ecommerce.domain.member.domain.Member;
import com.ecommerce.ecommerce.domain.product.domain.Product;
import com.ecommerce.ecommerce.domain.product.dto.ProductCreateDTO;
import com.ecommerce.ecommerce.domain.product.repository.ProductRepository;
import com.ecommerce.ecommerce.domain.product.constant.DeliveryTypeEnum;
import com.ecommerce.ecommerce.domain.product.dto.ProductsRequest;
import com.ecommerce.ecommerce.domain.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductResponse> getProducts(ProductsRequest dto){
        DeliveryTypeEnum deliveryType = dto.getDeliveryTypeEnum();
        boolean isRocket = dto.isRocket();


        List<Product> products = getProductsSortByDeliveryType(deliveryType, isRocket);

        return products.stream()
                .map(ProductResponse::toResponse)
                .collect(Collectors.toList());
    }

    private List<Product> getProductsSortByDeliveryType (DeliveryTypeEnum deliveryType, boolean isRocket) {
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
    public List<ProductResponse> searchProductsByName(String name) throws Exception{
        Optional<List<Product>> products = productRepository.findByNameLike(name);
        if(products.isEmpty())
            throw new Exception("empty.");
        return ProductResponse.toList(products.get());
    }

    @Override
    public ProductResponse createProduct(Member member, ProductCreateDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        return ProductResponse.toResponse(productRepository.save(product));
    }

    public Product getProduct(long id){
        return productRepository.findById(id).get();
    }
    public boolean checkIsProductExist(long id){
        return productRepository.findById(id).isPresent();
    }
}
