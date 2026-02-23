package com.jacklin.loginguard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // 告诉 Spring：这是一个全局的异常拦截器 (急诊科)
public class GlobalExceptionHandler {

    // 专门处理 AuthException 这种病
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthException(AuthException e) {
        // 组装一个标准的 JSON 错误信息
        Map<String, Object> response = new HashMap<>();
        response.put("status", 401);
        response.put("error", "Unauthorized");
        response.put("message", e.getMessage()); // 这里就是我们抛出异常时写的具体原因

        // 返回 401 状态码和 JSON 数据
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}