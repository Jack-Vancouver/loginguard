package com.jacklin.loginguard.controller;

import com.jacklin.loginguard.dto.LoginRequest;
import com.jacklin.loginguard.entity.LoginAttempt;
import com.jacklin.loginguard.entity.User;
import com.jacklin.loginguard.repository.LoginAttemptRepository;
import com.jacklin.loginguard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth") // 这个类下的所有接口都以 /auth 开头
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    // 接口地址：POST http://localhost:10000/auth/register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        // @RequestBody 的作用：把前端发来的 JSON 数据自动转成 Java 的 User 对象
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        // 1. 获取客户端的 IP 地址
        String ip = httpRequest.getRemoteAddr();

        // 2. 调用 Service，传入 IP
        User user = userService.login(request, ip);

        if (user == null) {
            return "Login Failed! Wrong username or password.";
        }

        return "Login Success! Welcome " + user.getUsername();
    }

    // 访问地址：GET http://localhost:10000/auth/logs
    @GetMapping("/logs")
    public List<LoginAttempt> getLogs() {
        return loginAttemptRepository.findAll();
    }
}