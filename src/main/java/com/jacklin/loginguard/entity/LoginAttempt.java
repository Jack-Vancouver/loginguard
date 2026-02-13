package com.jacklin.loginguard.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "login_attempts")
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 谁在尝试登录

    private boolean success; // 成功了吗？(true/false)

    private String ipAddress; // 他的 IP 是多少

    private LocalDateTime createdAt; // 什么时候发生的
}