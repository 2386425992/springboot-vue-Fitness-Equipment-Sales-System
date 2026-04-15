package com.niuniu.fitness_shop.service;

import com.niuniu.fitness_shop.entity.*;
import com.niuniu.fitness_shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Page<Order> getOrdersByUserId(Long userId, int page, int size) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findByUser(user, pageable);
    }

    public List<Order> getAllOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Page<Order> getOrdersByStatus(Integer status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findByStatus(status, pageable);
    }

    public Page<Order> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public Order getOrderByOrderNo(String orderNo) {
        Optional<Order> order = orderRepository.findByOrderNo(orderNo);
        return order.orElse(null);
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        return orderItemRepository.findByOrder(order);
    }

    @Transactional
    public Map<String, Object> createOrder(Long userId, String address, String phone, String receiverName, String remark) {
        Map<String, Object> result = new HashMap<>();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }

        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems == null || cartItems.isEmpty()) {
            result.put("code", 400);
            result.put("message", "购物车为空");
            return result;
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Cart cart : cartItems) {
            Product product = cart.getProduct();
            if (product.getStockQuantity() < cart.getQuantity()) {
                result.put("code", 400);
                result.put("message", "商品 " + product.getName() + " 库存不足");
                return result;
            }
            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
        }

        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setStatus(0);
        order.setAddress(address);
        order.setPhone(phone);
        order.setReceiverName(receiverName);
        order.setRemark(remark);

        orderRepository.save(order);

        for (Cart cart : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cart.getProduct());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setUnitPrice(cart.getProduct().getPrice());
            orderItem.setTotalPrice(cart.getProduct().getPrice().multiply(new BigDecimal(cart.getQuantity())));
            orderItemRepository.save(orderItem);

            Product product = cart.getProduct();
            product.setStockQuantity(product.getStockQuantity() - cart.getQuantity());
            productRepository.save(product);
        }

        cartRepository.deleteByUser(user);

        result.put("code", 200);
        result.put("message", "订单创建成功");
        result.put("data", order);
        return result;
    }

    @Transactional
    public Map<String, Object> payOrder(Long orderId) {
        Map<String, Object> result = new HashMap<>();

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            result.put("code", 404);
            result.put("message", "订单不存在");
            return result;
        }

        Order o = order.get();
        if (o.getStatus() != 0) {
            result.put("code", 400);
            result.put("message", "订单状态不正确");
            return result;
        }

        o.setStatus(1);
        o.setPaymentTime(LocalDateTime.now());
        orderRepository.save(o);

        result.put("code", 200);
        result.put("message", "支付成功");
        return result;
    }

    @Transactional
    public Map<String, Object> shipOrder(Long orderId) {
        Map<String, Object> result = new HashMap<>();

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            result.put("code", 404);
            result.put("message", "订单不存在");
            return result;
        }

        Order o = order.get();
        if (o.getStatus() != 1) {
            result.put("code", 400);
            result.put("message", "订单状态不正确");
            return result;
        }

        o.setStatus(2);
        o.setShipTime(LocalDateTime.now());
        orderRepository.save(o);

        result.put("code", 200);
        result.put("message", "发货成功");
        return result;
    }

    @Transactional
    public Map<String, Object> completeOrder(Long orderId) {
        Map<String, Object> result = new HashMap<>();

        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            result.put("code", 404);
            result.put("message", "订单不存在");
            return result;
        }

        Order o = order.get();
        if (o.getStatus() != 2) {
            result.put("code", 400);
            result.put("message", "订单状态不正确");
            return result;
        }

        o.setStatus(3);
        o.setCompleteTime(LocalDateTime.now());
        orderRepository.save(o);

        result.put("code", 200);
        result.put("message", "订单完成");
        return result;
    }

    private String generateOrderNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "ORD" + LocalDateTime.now().format(formatter) + (int)(Math.random() * 1000);
    }
}
