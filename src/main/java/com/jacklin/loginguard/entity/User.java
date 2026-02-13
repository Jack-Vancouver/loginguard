package com.jacklin.loginguard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data // Lombok 这里的魔法：自动帮你生成 Get/Set 方法，不用手写了
@Entity // 告诉 Spring Boot：这是一个数据库表
@Table(name = "users") // 数据库里的表名叫 users
public class User {

    @Id // 这是主键 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID 自增 (1, 2, 3...)
    private Long id;

    @Column(unique = true, nullable = false) // 用户名必须唯一，且不能为空
    private String username;

    @Column(nullable = false)
    private String password; // 密码 (之后我们会加密存进去)

    private String role; // 角色，比如 "ADMIN" 或 "USER"
}