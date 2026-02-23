package com.jacklin.loginguard.config;

import com.jacklin.loginguard.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component // 交给 Spring 管理
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // 每一个进入后端的请求，都要先经过这个保安的盘问
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 检查游客有没有戴手环 (HTTP 头里的 Authorization 字段)
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 国际标准格式：手环开头必须带 "Bearer "（持票人）这几个字
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // 剥去 "Bearer " 外衣，拿到真正的 JWT 乱码
            try {
                username = jwtUtil.extractUsername(token); // 用扫描仪尝试提取名字
            } catch (Exception e) {
                System.out.println("🚨 提取 Token 失败: " + e.getMessage());
            }
        }

        // 2. 如果手环里有名字，且该游客当前还没被系统登记过
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 3. 鉴定手环的真伪和是否过期
            if (jwtUtil.validateToken(token)) {

                // 4. 手环是真的！给他发放内部通行证 (告诉 Spring Security 这人合法)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>()); // 空的权限列表
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 5. 把通行证挂在墙上，后面的系统就知道这人是谁了
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. 保安检查完毕，放行！(让他去 Controller 里执行业务)
        filterChain.doFilter(request, response);
    }
}