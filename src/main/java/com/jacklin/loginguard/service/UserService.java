package com.jacklin.loginguard.service;

import com.jacklin.loginguard.dto.LoginRequest;
import com.jacklin.loginguard.entity.LoginAttempt;
import com.jacklin.loginguard.entity.User;
import com.jacklin.loginguard.repository.LoginAttemptRepository;
import com.jacklin.loginguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // 告诉 Spring：这是一个业务类，请把它管理起来
public class UserService {

    @Autowired // 自动把刚才写的 UserRepository 注入进来
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptRepository loginAttemptRepository; // 注入日志管理员

    // 注册功能的逻辑
    public User register(User user) {
        // 1. 检查用户名是否存在（先省略，以后加）
        // 2. 密码加密（先省略，下一阶段加）

        // 3. 直接保存到数据库
        return userRepository.save(user);
    }

    public User login(LoginRequest request, String ipAddress) {
        User user = userRepository.findByUsername(request.getUsername());

        // 判断登录是否成功
        boolean loginSuccess = (user != null && user.getPassword().equals(request.getPassword()));

        // === 关键一步：不管成功失败，都记录日志 ===
        LoginAttempt attempt = new LoginAttempt();
        attempt.setUsername(request.getUsername());
        attempt.setSuccess(loginSuccess);
        attempt.setIpAddress(ipAddress);
        attempt.setCreatedAt(LocalDateTime.now()); // 当前时间

        loginAttemptRepository.save(attempt); // 存入数据库！
        // =======================================

        if (!loginSuccess) {
            return null;
        }

        return user;
    }
}