package com.niuniu.fitness_shop.controller;

import com.niuniu.fitness_shop.entity.User;
import com.niuniu.fitness_shop.service.UserService;
import com.niuniu.fitness_shop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/info")
    public Map<String, Object> getUserInfo(@RequestHeader("Authorization") String token) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            token = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(token);
            User user = userService.getUserById(userId);
            
            if (user == null) {
                result.put("code", 404);
                result.put("message", "用户不存在");
                return result;
            }

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            userInfo.put("phone", user.getPhone());
            userInfo.put("role", user.getRole());

            result.put("code", 200);
            result.put("data", userInfo);
        } catch (Exception e) {
            result.put("code", 401);
            result.put("message", "无效的token");
        }
        
        return result;
    }

    @PutMapping("/update")
    public Map<String, Object> updateUserInfo(@RequestHeader("Authorization") String token,
                                               @RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            token = token.replace("Bearer ", "");
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            String email = params.get("email");
            String phone = params.get("phone");
            String address = params.get("address");
            
            return userService.updateUserInfo(userId, email, phone, address);
        } catch (Exception e) {
            result.put("code", 401);
            result.put("message", "无效的token");
            return result;
        }
    }
}
