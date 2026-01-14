package com.example.hospitaljpa.controller;

import org.springframework.web.bind.annotation.*;
import com.example.hospitaljpa.dto.request.AssignShiftRequest;
import com.example.hospitaljpa.dto.response.ShiftUserResponse;
import com.example.hospitaljpa.service.ShiftUserService;
import static com.example.hospitaljpa.util.ApiConstants.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(SHIFT_USERS)
@Tag(name = "ShiftAssignments", description = "Assign users to shifts and manage assignments")
@Slf4j
public class ShiftUserController {

    private final ShiftUserService service;

    public ShiftUserController(ShiftUserService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Assign user to shift", description = "Create an assignment linking a user to a shift")
    @ApiResponse(responseCode = "200", description = "Assignment created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShiftUserResponse.class)))
    public ShiftUserResponse assign(@RequestBody AssignShiftRequest request) {
        log.debug("POST {} - payload: {}", SHIFT_USERS, request);
        var assignment = service.assign(request.getShiftId(), request.getUserId());
        log.info("Assignment created via controller id={}", assignment.getId());
        return new ShiftUserResponse(
            assignment.getId(),
            assignment.getShift().getId(),
            assignment.getUser().getId()
        );
    }
}
