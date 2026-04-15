package com.niuniu.fitness_shop.service;

import com.niuniu.fitness_shop.entity.Cart;
import com.niuniu.fitness_shop.entity.Product;
import com.niuniu.fitness_shop.entity.User;
import com.niuniu.fitness_shop.repository.CartRepository;
import com.niuniu.fitness_shop.repository.ProductRepository;
import com.niuniu.fitness_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getCartByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return cartRepository.findByUser(user);
    }

    public Map<String, Object> addToCart(Long userId, Long productId, Integer quantity) {
        Map<String, Object> result = new HashMap<>();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            result.put("code", 404);
            result.put("message", "商品不存在");
            return result;
        }

        if (product.getStockQuantity() < quantity) {
            result.put("code", 400);
            result.put("message", "库存不足");
            return result;
        }

        Optional<Cart> existingCart = cartRepository.findByUserAndProductId(user, productId);
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cartRepository.save(cart);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        }

        result.put("code", 200);
        result.put("message", "添加到购物车成功");
        return result;
    }

    public Map<String, Object> updateCartQuantity(Long cartId, Integer quantity) {
        Map<String, Object> result = new HashMap<>();

        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            result.put("code", 404);
            result.put("message", "购物车项不存在");
            return result;
        }

        Cart c = cart.get();
        if (c.getProduct().getStockQuantity() < quantity) {
            result.put("code", 400);
            result.put("message", "库存不足");
            return result;
        }

        c.setQuantity(quantity);
        cartRepository.save(c);

        result.put("code", 200);
        result.put("message", "更新成功");
        return result;
    }

    public Map<String, Object> removeFromCart(Long cartId) {
        Map<String, Object> result = new HashMap<>();

        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            result.put("code", 404);
            result.put("message", "购物车项不存在");
            return result;
        }

        cartRepository.delete(cart.get());
        result.put("code", 200);
        result.put("message", "删除成功");
        return result;
    }

    public Map<String, Object> clearCart(Long userId) {
        Map<String, Object> result = new HashMap<>();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }

        cartRepository.deleteByUser(user);
        result.put("code", 200);
        result.put("message", "清空成功");
        return result;
    }
}
