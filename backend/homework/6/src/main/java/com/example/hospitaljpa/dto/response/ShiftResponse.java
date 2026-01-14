package com.example.hospitaljpa.dto.response;

import java.time.LocalDate;

public record ShiftResponse(
        Long id,
        String shiftName,
        LocalDate startDate,
        LocalDate endDate,
        String shiftType
) {}
