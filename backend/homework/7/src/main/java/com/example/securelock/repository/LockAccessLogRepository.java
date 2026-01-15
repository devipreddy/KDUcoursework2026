package com.example.securelock.repository;

import com.example.securelock.model.LockAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LockAccessLogRepository extends JpaRepository<LockAccessLog, String> {
}
