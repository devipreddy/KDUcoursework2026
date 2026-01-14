package com.example.hospitaljpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitaljpa.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}

