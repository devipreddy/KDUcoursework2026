package com.example.hospitaljpa.repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import com.example.hospitaljpa.entities.ShiftEntity;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {

        @Query("""
        SELECT s FROM ShiftEntity s
        WHERE s.startDate = :start
          AND s.endDate <= :end
        ORDER BY s.shiftName ASC
    """)
    List<ShiftEntity> findTop3NewYearShifts(
            LocalDate start,
            LocalDate end,
            Pageable pageable
    );
}

