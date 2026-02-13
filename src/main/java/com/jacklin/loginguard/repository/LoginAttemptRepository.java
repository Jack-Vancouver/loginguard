package com.jacklin.loginguard.repository;

import com.jacklin.loginguard.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    // 以后我们可以加：查找最近10条日志的功能
    // List<LoginAttempt> findTop10ByOrderByCreatedAtDesc();
}