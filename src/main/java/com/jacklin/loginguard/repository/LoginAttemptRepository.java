package com.jacklin.loginguard.repository;

import com.jacklin.loginguard.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    // 统计某个 IP 在某个时间之后 (After) 失败 (SuccessFalse) 的次数
    int countByIpAddressAndSuccessFalseAndCreatedAtAfter(String ipAddress, LocalDateTime time);
}