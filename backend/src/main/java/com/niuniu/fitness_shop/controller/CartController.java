package com.niuniu.fitness_shop.controller;

import com.niuniu.fitness_shop.entity.Cart;
import com.niuniu.fitness_shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Map<String, Object> getCart(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        List<Cart> cart = cartService.getCartByUserId(userId);
        
        if (cart == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }
        
        result.put("code", 200);
        result.put("data", cart);
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> addToCart(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long productId = Long.valueOf(params.get("productId").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());
        
        return cartService.addToCart(userId, productId, quantity);
    }

    @PutMapping("/update/{cartId}")
    public Map<String, Object> updateQuantity(@PathVariable Long cartId,
                                               @RequestBody Map<String, Integer> params) {
        Integer quantity = params.get("quantity");
        return cartService.updateCartQuantity(cartId, quantity);
    }

    @DeleteMapping("/{cartId}")
    public Map<String, Object> removeFromCart(@PathVariable Long cartId) {
        return cartService.removeFromCart(cartId);
    }

    @DeleteMapping("/clear/{userId}")
    public Map<String, Object> clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }
}
