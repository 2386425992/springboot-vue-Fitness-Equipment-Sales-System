package com.niuniu.fitness_shop.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static Map<String, Object> success() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "操作成功");
        return response;
    }

    public static Map<String, Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "操作成功");
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> success(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 500);
        response.put("message", message);
        return response;
    }

    public static Map<String, Object> error(Integer code, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        return response;
    }

    public static Map<String, Object> unauthorized(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 401);
        response.put("message", message);
        return response;
    }

    public static Map<String, Object> forbidden(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 403);
        response.put("message", message);
        return response;
    }
}
