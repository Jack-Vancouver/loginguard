package com.jacklin.loginguard.controller;

import com.jacklin.loginguard.dto.LoginRequest;
import com.jacklin.loginguard.entity.LoginAttempt;
import com.jacklin.loginguard.entity.User;
import com.jacklin.loginguard.repository.LoginAttemptRepository;
import com.jacklin.loginguard.service.UserService;
// === 新增导入 JWT 工具 ===
import com.jacklin.loginguard.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    // === 新增：把手环制造机拿过来 ===
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();

        // 1. 去验证账号密码 (如果错了，急诊科会接手，下面代码不执行)
        User user = userService.login(request, ip);

        // 2. 走到这里说明密码绝对正确！生成 JWT 手环！
        String token = jwtUtil.generateToken(user.getUsername());

        // 3. 把手环打包成漂亮的 JSON 格式发给前端
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login Success!");
        response.put("username", user.getUsername());
        response.put("token", token); // 这就是传说中的 JWT 令牌！

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logs")
    public List<LoginAttempt> getLogs() {
        return loginAttemptRepository.findAll();
    }
}