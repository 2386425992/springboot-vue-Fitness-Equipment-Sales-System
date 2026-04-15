package com.niuniu.fitness_shop.repository;

import com.niuniu.fitness_shop.entity.Order;
import com.niuniu.fitness_shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
