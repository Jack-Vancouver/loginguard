package com.jacklin.loginguard.repository;

import com.jacklin.loginguard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 继承 JpaRepository，Spring Boot 就会自动帮我们实现增删改查
// <User, Long> 的意思是：管理 User 表，主键 ID 是 Long 类型
public interface UserRepository extends JpaRepository<User, Long> {
    // 这里现在是空的，但它已经拥有了 save(), findAll(), findById() 等几十个功能！
    // 就像你继承了亿万家产，虽然口袋里没钱，但银行卡里全是钱。

    // 我们可以加一个功能：根据用户名找用户
    User findByUsername(String username);
}