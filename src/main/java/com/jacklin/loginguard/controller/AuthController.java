package com.jacklin.loginguard.controller;

import com.jacklin.loginguard.dto.LoginRequest;
import com.jacklin.loginguard.entity.LoginAttempt;
import com.jacklin.loginguard.entity.User;
import com.jacklin.loginguard.repository.LoginAttemptRepository;
import com.jacklin.loginguard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();

        // 调用 service 进行登录。如果失败，它会直接抛出异常被“急诊科”接走，下面的代码就不会执行了！
        User user = userService.login(request, ip);

        // 如果能走到这里，说明一定成功了
        return ResponseEntity.ok("Login Success! Welcome " + user.getUsername());
    }

    // 访问地址：GET http://localhost:10000/auth/logs
    @GetMapping("/logs")
    public List<LoginAttempt> getLogs() {
        return loginAttemptRepository.findAll();
    }
}