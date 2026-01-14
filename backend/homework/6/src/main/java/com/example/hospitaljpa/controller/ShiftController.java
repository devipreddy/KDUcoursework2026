package com.example.hospitaljpa.controller;

import org.springframework.web.bind.annotation.*;
import com.example.hospitaljpa.dto.request.CreateShiftRequest;
import com.example.hospitaljpa.dto.response.ShiftResponse;
import com.example.hospitaljpa.service.ShiftService;
import static com.example.hospitaljpa.util.ApiConstants.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(SHIFTS)
@Tag(name = "Shifts", description = "Create and query shifts")
@Slf4j
public class ShiftController {

    private final ShiftService service;

    public ShiftController(ShiftService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create shift", description = "Create a shift with name, start/end dates and shift type")
    @ApiResponse(responseCode = "200", description = "Shift created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShiftResponse.class)))
    public ShiftResponse create(@RequestBody CreateShiftRequest request) {
        log.debug("POST {} - payload: {}", SHIFTS, request);
        var shift = service.create(request);
        log.info("Shift created via controller id={}", shift.getId());
        return new ShiftResponse(
            shift.getId(),
            shift.getShiftName(),
            shift.getStartDate(),
            shift.getEndDate(),
            shift.getShiftType().getName()
        );
    }


    @GetMapping("/new-year-top3")
    @Operation(summary = "Top 3 New Year shifts", description = "Retrieve top 3 shifts for New Year period based on business criteria")
    @ApiResponse(responseCode = "200", description = "List of top 3 shifts",
            content = @Content(mediaType = "application/json"))
    public List<ShiftResponse> getTop3NewYearShifts() {
        log.debug("GET {}/new-year-top3 requested", SHIFTS);
        return service.getTop3NewYearShifts();
    }

}

