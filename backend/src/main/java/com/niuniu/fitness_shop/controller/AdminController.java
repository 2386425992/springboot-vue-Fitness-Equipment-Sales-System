package com.niuniu.fitness_shop.controller;

import com.niuniu.fitness_shop.entity.Order;
import com.niuniu.fitness_shop.entity.Product;
import com.niuniu.fitness_shop.service.LogService;
import com.niuniu.fitness_shop.service.OrderService;
import com.niuniu.fitness_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LogService logService;

    @GetMapping("/products")
    public Map<String, Object> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        Page<Product> products = productService.getAllProducts(page, size);
        result.put("code", 200);
        result.put("data", products.getContent());
        result.put("total", products.getTotalElements());
        result.put("pageNum", products.getNumber());
        result.put("pageSize", products.getSize());
        return result;
    }

    @PostMapping("/products/add")
    public Map<String, Object> addProduct(@RequestBody Product product) {
        logService.addLog("admin", "添加商品", "添加商品: " + product.getName(), "127.0.0.1");
        return productService.addProduct(product);
    }

    @PutMapping("/products/update/{id}")
    public Map<String, Object> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        logService.addLog("admin", "更新商品", "更新商品ID: " + id, "127.0.0.1");
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/products/delete/{id}")
    public Map<String, Object> deleteProduct(@PathVariable Long id) {
        logService.addLog("admin", "删除商品", "删除商品ID: " + id, "127.0.0.1");
        return productService.deleteProduct(id);
    }

    @GetMapping("/orders")
    public Map<String, Object> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        Page<Order> orders = orderService.getAllOrders(page, size);
        result.put("code", 200);
        result.put("data", orders.getContent());
        result.put("total", orders.getTotalElements());
        result.put("pageNum", orders.getNumber());
        result.put("pageSize", orders.getSize());
        return result;
    }

    @GetMapping("/orders/status/{status}")
    public Map<String, Object> getOrdersByStatus(@PathVariable Integer status,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        Page<Order> orders = orderService.getOrdersByStatus(status, page, size);
        result.put("code", 200);
        result.put("data", orders.getContent());
        result.put("total", orders.getTotalElements());
        result.put("pageNum", orders.getNumber());
        result.put("pageSize", orders.getSize());
        return result;
    }

    @PostMapping("/orders/ship/{orderId}")
    public Map<String, Object> shipOrder(@PathVariable Long orderId) {
        logService.addLog("admin", "订单发货", "订单ID: " + orderId + " 已发货", "127.0.0.1");
        return orderService.shipOrder(orderId);
    }

    @PostMapping("/orders/complete/{orderId}")
    public Map<String, Object> completeOrder(@PathVariable Long orderId) {
        logService.addLog("admin", "订单完成", "订单ID: " + orderId + " 已完成", "127.0.0.1");
        return orderService.completeOrder(orderId);
    }

    @GetMapping("/logs")
    public Map<String, Object> getLogs(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", logService.getLogs(page, size));
        return result;
    }
}
