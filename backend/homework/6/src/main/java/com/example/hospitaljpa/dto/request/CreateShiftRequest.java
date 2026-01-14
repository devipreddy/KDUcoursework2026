package com.example.hospitaljpa.dto.request;

import java.time.LocalDate;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateShiftRequest {
    private String shiftName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long shiftTypeId;
}

