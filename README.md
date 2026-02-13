# LoginGuard - Authentication & Security System

## 简介 (Introduction)
LoginGuard 是一个基于 Spring Boot 的企业级身份认证与安全审计系统。旨在模拟真实的登录安全防护场景，包括用户认证、审计日志记录以及防御暴力破解等功能。

## 核心功能 (Key Features)
* **用户注册与登录**: 基于 RESTful API 的标准认证流程。
* **安全审计 (Audit Logging)**: 自动记录每一次登录尝试（成功/失败、IP地址、时间戳）。
* **安全防御**: (开发中) 针对暴力破解和撞库攻击的自动拦截机制。
* **容器化部署**: 使用 Docker Compose 一键启动数据库环境。

## 技术栈 (Tech Stack)
* **Language**: Java 21
* **Framework**: Spring Boot 3.4.2
* **Database**: PostgreSQL 15 (Docker)
* **ORM**: Spring Data JPA
* **Tools**: Postman, IntelliJ IDEA, Maven

## 快速开始 (Quick Start)

### 1. 环境要求
* Java 21+
* Docker Desktop

### 2. 启动数据库
```bash
docker-compose up -d
```
### 3. 运行项目
在 IDEA 中运行 LoginguardApplication.java。

### 4. API 测试
* **注册**: POST /auth/register

* **登录**: POST /auth/login

* **查看日志**: GET /auth/logs