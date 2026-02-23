package com.jacklin.loginguard.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 1. 我们的绝密印章（千万不能泄露）
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 2. 过期时间：1 小时
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    // --- 之前写好的：发手环 ---
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // === 新增：保安用扫描仪提取手环里的用户名 ===
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // 用我们的印章去解密
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 拿到用户名
    }

    // === 新增：保安验证手环是不是伪造的，或者是不是过期了 ===
    public boolean validateToken(String token) {
        try {
            // 只要能用我们的印章正常解析，且没抛出异常，就是真手环！
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("🚨 发现伪造或过期的 JWT 令牌: " + e.getMessage());
            return false;
        }
    }
}