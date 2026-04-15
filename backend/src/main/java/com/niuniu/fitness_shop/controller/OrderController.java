package com.niuniu.fitness_shop.controller;

import com.niuniu.fitness_shop.entity.Order;
import com.niuniu.fitness_shop.entity.OrderItem;
import com.niuniu.fitness_shop.service.OrderService;
import com.niuniu.fitness_shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public Map<String, Object> getMyOrders(@RequestHeader("Authorization") String token,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            token = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(token);
            Page<Order> orders = orderService.getOrdersByUserId(userId, page, size);
            
            if (orders == null) {
                result.put("code", 404);
                result.put("message", "用户不存在");
                return result;
            }
            
            result.put("code", 200);
            result.put("data", orders.getContent());
            result.put("total", orders.getTotalElements());
            result.put("pageNum", orders.getNumber());
            result.put("pageSize", orders.getSize());
        } catch (Exception e) {
            result.put("code", 401);
            result.put("message", "无效的token");
        }
        
        return result;
    }

    @GetMapping("/{orderId}")
    public Map<String, Object> getOrderDetail(@PathVariable Long orderId) {
        Map<String, Object> result = new HashMap<>();
        Order order = orderService.getOrderById(orderId);
        
        if (order == null) {
            result.put("code", 404);
            result.put("message", "订单不存在");
            return result;
        }
        
        List<OrderItem> items = orderService.getOrderItems(orderId);
        
        Map<String, Object> data = new HashMap<>();
        data.put("order", order);
        data.put("items", items);
        
        result.put("code", 200);
        result.put("data", data);
        return result;
    }

    @PostMapping("/create")
    public Map<String, Object> createOrder(@RequestHeader("Authorization") String token,
                                            @RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            token = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            String address = params.get("address");
            String phone = params.get("phone");
            String receiverName = params.get("receiverName");
            String remark = params.get("remark");
            
            return orderService.createOrder(userId, address, phone, receiverName, remark);
        } catch (Exception e) {
            result.put("code", 401);
            result.put("message", "无效的token");
            return result;
        }
    }

    @PostMapping("/pay/{orderId}")
    public Map<String, Object> payOrder(@PathVariable Long orderId) {
        return orderService.payOrder(orderId);
    }
}
