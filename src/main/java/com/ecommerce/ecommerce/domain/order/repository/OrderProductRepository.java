package com.ecommerce.ecommerce.domain.order.repository;

import com.ecommerce.ecommerce.domain.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

}
