package com.jacklin.loginguard.service;

import com.jacklin.loginguard.dto.LoginRequest;
import com.jacklin.loginguard.entity.User;
import com.jacklin.loginguard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 加了这个魔法注解，测试完的数据会自动“撤回”，不会把数据库搞脏
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLoginSuccess() {
        // 1. 先在模拟数据库里造一个假用户
        User user = new User();
        user.setUsername("test_user_success");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole("USER");
        userRepository.save(user);

        // 2. 准备登录请求
        LoginRequest request = new LoginRequest();
        request.setUsername("test_user_success");
        request.setPassword("password123");

        // 3. 调用登录方法
        User result = userService.login(request, "127.0.0.1");

        // 4. 断言：如果返回不是 null，且用户名对得上，说明测试通过！
        assertNotNull(result, "登录不应返回 null");
        assertEquals("test_user_success", result.getUsername());

        System.out.println("✅ 正常登录测试通过！");
    }

    @Test
    public void testLoginWrongPassword() {
        // 1. 造用户
        User user = new User();
        user.setUsername("test_user_fail");
        user.setPassword(passwordEncoder.encode("password123"));
        userRepository.save(user);

        // 2. 准备错误的请求
        LoginRequest request = new LoginRequest();
        request.setUsername("test_user_fail");
        request.setPassword("wrong_password"); // 错的密码

        // 3. 断言：应该抛出异常
        assertThrows(RuntimeException.class, () -> {
            userService.login(request, "127.0.0.1");
        });

        System.out.println("✅ 密码错误拦截测试通过！");
    }
}
