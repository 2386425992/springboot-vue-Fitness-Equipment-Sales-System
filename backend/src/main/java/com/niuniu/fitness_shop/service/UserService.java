package com.niuniu.fitness_shop.service;

import com.niuniu.fitness_shop.entity.User;
import com.niuniu.fitness_shop.repository.UserRepository;
import com.niuniu.fitness_shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Map<String, Object> register(String username, String password, String email, String phone) {
        Map<String, Object> result = new HashMap<>();
        
        if (userRepository.existsByUsername(username)) {
            result.put("code", 400);
            result.put("message", "用户名已存在");
            return result;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(0);

        userRepository.save(user);

        result.put("code", 200);
        result.put("message", "注册成功");
        return result;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
            return result;
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
            return result;
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getRole());
        
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("username", user.getUsername());
        data.put("userId", user.getId());
        data.put("role", user.getRole());

        result.put("code", 200);
        result.put("message", "登录成功");
        result.put("data", data);
        
        return result;
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public Map<String, Object> updateUserInfo(Long userId, String email, String phone, String address) {
        Map<String, Object> result = new HashMap<>();
        
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            result.put("code", 404);
            result.put("message", "用户不存在");
            return result;
        }

        User user = userOpt.get();
        if (email != null) {
            user.setEmail(email);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (address != null) {
            user.setAddress(address);
        }

        userRepository.save(user);

        result.put("code", 200);
        result.put("message", "更新成功");
        return result;
    }
}
