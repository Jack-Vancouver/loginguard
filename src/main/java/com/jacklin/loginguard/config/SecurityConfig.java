package com.jacklin.loginguard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // === 新增：把刚才写的保安大队长叫过来 ===
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                // === 修改 1：重新制定放行规则 ===
                .authorizeHttpRequests(auth -> auth
                        // 登录和注册接口，所有人大摇大摆直接进
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        // 其他任何接口（比如查日志的 /auth/logs），必须买票验证！
                        .anyRequest().authenticated()
                )
                // === 修改 2：告诉系统我们用 JWT，不保存传统的 Session 状态 ===
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // === 修改 3：把我们的保安，安排在 Spring 默认的安检门之前 ===
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}