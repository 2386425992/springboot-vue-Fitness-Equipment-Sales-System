package com.niuniu.fitness_shop.repository;

import com.niuniu.fitness_shop.entity.Cart;
import com.niuniu.fitness_shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    
    Optional<Cart> findByUserAndProductId(User user, Long productId);
    
    void deleteByUser(User user);
}
