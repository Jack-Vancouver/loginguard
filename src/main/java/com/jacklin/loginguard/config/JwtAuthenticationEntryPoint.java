package com.jacklin.loginguard.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // 交给 Spring 管理
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // 当游客（前端）被保安拦下时，就会触发这个方法
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 1. 设置响应格式为 JSON，并支持中文
        response.setContentType("application/json;charset=UTF-8");
        // 2. 设置 HTTP 状态码为 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 3. 用大喇叭喊出标准的 JSON 错误信息
        response.getWriter().write("{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"访问被拒绝：请先登录或携带有效的 JWT 令牌！\"}");
    }
}