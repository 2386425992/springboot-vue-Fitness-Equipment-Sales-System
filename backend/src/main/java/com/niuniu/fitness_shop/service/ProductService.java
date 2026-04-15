package com.niuniu.fitness_shop.service;

import com.niuniu.fitness_shop.entity.Product;
import com.niuniu.fitness_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return productRepository.findByStatus(1, pageable);
    }

    public Page<Product> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return productRepository.searchProducts(keyword, pageable);
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return productRepository.findAll(pageable);
    }

    public Map<String, Object> addProduct(Product product) {
        Map<String, Object> result = new HashMap<>();
        productRepository.save(product);
        result.put("code", 200);
        result.put("message", "添加成功");
        result.put("data", product);
        return result;
    }

    public Map<String, Object> updateProduct(Long id, Product product) {
        Map<String, Object> result = new HashMap<>();
        Optional<Product> existingProduct = productRepository.findById(id);
        
        if (existingProduct.isEmpty()) {
            result.put("code", 404);
            result.put("message", "商品不存在");
            return result;
        }

        Product p = existingProduct.get();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setStockQuantity(product.getStockQuantity());
        p.setCategory(product.getCategory());
        p.setImage(product.getImage());
        p.setStatus(product.getStatus());

        productRepository.save(p);

        result.put("code", 200);
        result.put("message", "更新成功");
        result.put("data", p);
        return result;
    }

    public Map<String, Object> deleteProduct(Long id) {
        Map<String, Object> result = new HashMap<>();
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isEmpty()) {
            result.put("code", 404);
            result.put("message", "商品不存在");
            return result;
        }

        productRepository.delete(product.get());
        result.put("code", 200);
        result.put("message", "删除成功");
        return result;
    }

    public Map<String, Object> updateStock(Long productId, Integer quantity) {
        Map<String, Object> result = new HashMap<>();
        Optional<Product> product = productRepository.findById(productId);
        
        if (product.isEmpty()) {
            result.put("code", 404);
            result.put("message", "商品不存在");
            return result;
        }

        Product p = product.get();
        p.setStockQuantity(p.getStockQuantity() - quantity);
        productRepository.save(p);

        result.put("code", 200);
        result.put("message", "库存更新成功");
        return result;
    }
}
