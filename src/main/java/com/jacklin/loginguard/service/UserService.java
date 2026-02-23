package com.jacklin.loginguard.service;

import com.jacklin.loginguard.entity.LoginAttempt;
import com.jacklin.loginguard.entity.User;
import com.jacklin.loginguard.dto.LoginRequest;
import com.jacklin.loginguard.repository.LoginAttemptRepository;
import com.jacklin.loginguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        // 注册时加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(LoginRequest request, String ip) {
        // === 1. 暴力破解防御检查 ===
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        int failCount = loginAttemptRepository.countByIpAddressAndSuccessFalseAndCreatedAtAfter(ip, fiveMinutesAgo);

        if (failCount >= 3) {
            System.out.println("🚨 [安全拦截] IP: " + ip + " 尝试次数过多，已封锁！");
            return null; // 直接拒绝，连数据库都不用查
        }
        // ==========================

        User user = userRepository.findByUsername(request.getUsername());

        // === 2. 密码验证 (使用 matches 方法) ===
        boolean loginSuccess = false;
        if (user != null) {
            loginSuccess = passwordEncoder.matches(request.getPassword(), user.getPassword());
        }

        // === 3. 记录日志 ===
        LoginAttempt attempt = new LoginAttempt();
        attempt.setUsername(request.getUsername());
        attempt.setSuccess(loginSuccess);
        attempt.setIpAddress(ip);
        attempt.setCreatedAt(LocalDateTime.now());
        loginAttemptRepository.save(attempt);

        if (!loginSuccess) {
            return null;
        }

        return user;
    }
}