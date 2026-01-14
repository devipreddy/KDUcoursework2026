package com.example.hospitaljpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitaljpa.entities.ShiftUserEntity;

public interface ShiftUserRepository extends JpaRepository<ShiftUserEntity, Long> {}

