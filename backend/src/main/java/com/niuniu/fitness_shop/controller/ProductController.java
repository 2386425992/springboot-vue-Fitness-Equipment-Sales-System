package com.niuniu.fitness_shop.controller;

import com.niuniu.fitness_shop.entity.Product;
import com.niuniu.fitness_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Map<String, Object> getProducts(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        Page<Product> products = productService.getProducts(page, size);
        result.put("code", 200);
        result.put("data", products.getContent());
        result.put("total", products.getTotalElements());
        result.put("pageNum", products.getNumber());
        result.put("pageSize", products.getSize());
        return result;
    }

    @GetMapping("/search")
    public Map<String, Object> searchProducts(@RequestParam String keyword,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = new HashMap<>();
        Page<Product> products = productService.searchProducts(keyword, page, size);
        result.put("code", 200);
        result.put("data", products.getContent());
        result.put("total", products.getTotalElements());
        result.put("pageNum", products.getNumber());
        result.put("pageSize", products.getSize());
        return result;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProductById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        Product product = productService.getProductById(id);
        
        if (product == null) {
            result.put("code", 404);
            result.put("message", "商品不存在");
            return result;
        }
        
        result.put("code", 200);
        result.put("data", product);
        return result;
    }

    @GetMapping("/category/{category}")
    public Map<String, Object> getByCategory(@PathVariable String category) {
        Map<String, Object> result = new HashMap<>();
        List<Product> products = productService.getProductsByCategory(category);
        result.put("code", 200);
        result.put("data", products);
        return result;
    }
}
