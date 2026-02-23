# LoginGuard - Enterprise Authentication & Security System
🌐 [English](#english-version) | [中文版](#中文版)

---

## <a id="english-version"></a> 🇺🇸 English Version

### 📖 Introduction
LoginGuard is an enterprise-grade identity authentication and security defense system built with Spring Boot.
This project simulates high-standard security scenarios in modern microservice architectures, implementing a robust backend defense line from scratch, including password hashing, stateless token authentication, high-precision audit logging, and anti-brute-force mechanisms.

### 🚀 Core Security Features
* **JWT Stateless Authentication**: Deprecated traditional sessions in favor of Filter-based JWT token issuance and multi-level authorization, returning standard 401/403 JSON responses.
* **Robust Password Hashing**: Leveraged Spring Security's core components to implement one-way irreversible encryption using the `BCrypt` algorithm with dynamic salt generation.
* **Active Brute-Force Protection**: Implemented real-time login frequency monitoring based on request IPs and precise timestamps. Automatically intercepts and blocks IPs at the business layer upon exceeding security thresholds (e.g., 3 failed attempts within 5 minutes).
* **Global Exception Handling & Audit Logging**:
    - Utilized `@RestControllerAdvice` to uniformly manage thrown exceptions, ensuring standard API contracts for decoupled front-end and back-end architectures.
    - Automatically records all login attempts (successes and failures) and their source IP addresses to the database, forming a complete traceable audit trail.
* **Automated Unit Testing**: Built core service layer test cases using `JUnit 5` and `@SpringBootTest` to ensure 100% reliability of the defense logic.

### 🛠️ Tech Stack
* **Language**: Java 21
* **Framework**: Spring Boot 3.4.2, Spring Security
* **Database**: PostgreSQL 15 (Dockerized)
* **ORM**: Spring Data JPA / Hibernate
* **Tools**: Maven, JWT (io.jsonwebtoken), Postman, JUnit 5

### 📦 Quick Start

**1. Prerequisites**
* Java 21+
* Docker & Docker Compose

**2. Start the Database Environment**
```bash
docker-compose up -d
```

**3. Run the Application**
Start `LoginguardApplication.java`. The service will run on `http://localhost:10000` by default.

**4. API Endpoints**

| Method | Endpoint | Authorization | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/register` | Public | User Registration (BCrypt hashed) |
| `POST` | `/auth/login` | Public | User Login (Issues JWT Token) |
| `GET` | `/auth/logs` | **Bearer Token** | Retrieve security audit logs |

> **Auth Note**: When accessing protected endpoints, please include `Authorization: Bearer <Your_JWT_Token>` in the HTTP Request Header.

---

## <a id="中文版"></a> 🇨🇳 中文版

### 📖 项目简介
LoginGuard 是一个基于 Spring Boot 构建的企业级身份认证与安全防御系统。
本项目旨在模拟现代微服务架构下的高标准安全防护场景，从零实现包含密码哈希脱敏、无状态令牌认证、高精度安全审计以及反暴力破解在内的完整后端安全防线。

### 🚀 核心硬核功能
* **JWT 无状态认证 (JSON Web Token)**: 弃用传统 Session，实现基于拦截器 (Filter) 的 JWT 令牌签发与多级鉴权，返回标准 401/403 JSON 响应。
* **高强度密码脱敏 (Password Hashing)**: 引入 Spring Security 核心组件，采用 `BCrypt` 算法与随机盐值 (Salt) 对用户密码进行单向不可逆加密。
* **主动防御与反暴力破解 (Brute-Force Protection)**: 基于请求 IP 与高精度时间戳，实现实时登录频次监控。超过安全阈值 (如 5 分钟内错误 3 次) 自动在业务层拦截并封禁该 IP。
* **全局异常处理与安全审计 (Audit & Exception Handling)**:
    - 核心接口采用 `@RestControllerAdvice` 统一接管抛出的异常，保障前后端分离 API 契约的规范性。
    - 数据库自动记录所有登录尝试 (无论成败) 及其来源 IP 地址，形成完整的溯源日志链。
* **自动化测试保障 (Unit Testing)**: 基于 `JUnit 5` 与 `@SpringBootTest` 构建核心服务层 (Service) 测试用例，确保防御逻辑 100% 可靠。

### 🛠️ 技术栈
* **Language**: Java 21
* **Framework**: Spring Boot 3.4.2, Spring Security
* **Database**: PostgreSQL 15 (Docker)
* **ORM**: Spring Data JPA / Hibernate
* **Tools**: Maven, JWT (io.jsonwebtoken), Postman, JUnit 5

### 📦 快速开始

**1. 环境要求**
* Java 21+
* Docker & Docker Compose

**2. 启动数据库环境**
```bash
docker-compose up -d
```

**3. 运行项目**
启动 `LoginguardApplication.java`。服务将默认运行在 `http://localhost:10000`。

**4. API 接口指北 (Endpoints)**

| 方法 | 路径 | 鉴权要求 | 功能说明 |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/register` | 公开 | 用户注册 (BCrypt 入库) |
| `POST` | `/auth/login` | 公开 | 用户登录 (下发 JWT 令牌) |
| `GET` | `/auth/logs` | **Bearer Token** | 获取安全审计日志 |

> **鉴权提示**: 访问受保护接口时，请在 HTTP Request Header 中携带 `Authorization: Bearer <Your_JWT_Token>`。