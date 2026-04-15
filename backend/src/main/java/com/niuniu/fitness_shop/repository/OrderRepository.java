package com.niuniu.fitness_shop.repository;

import com.niuniu.fitness_shop.entity.Order;
import com.niuniu.fitness_shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser(User user, Pageable pageable);
    
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    
    Page<Order> findByStatus(Integer status, Pageable pageable);
    
    Optional<Order> findByOrderNo(String orderNo);
}
