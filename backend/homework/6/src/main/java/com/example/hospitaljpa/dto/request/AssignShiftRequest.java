package com.example.hospitaljpa.dto.request;
import lombok.*;
@Data
@NoArgsConstructor
public class AssignShiftRequest {
    private Long shiftId;
    private Long userId;
}
