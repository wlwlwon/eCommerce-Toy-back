package com.ecommerce.ecommerce.domain.order.repository;

import com.ecommerce.ecommerce.domain.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
