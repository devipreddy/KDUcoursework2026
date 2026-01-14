package com.example.hospitaljpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospitaljpa.entities.ShiftTypeEntity;

public interface ShiftTypeRepository extends JpaRepository<ShiftTypeEntity, Long> {}
