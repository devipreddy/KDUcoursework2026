package com.example.hospitaljpa.controller;

import org.springframework.web.bind.annotation.*;
import com.example.hospitaljpa.dto.request.CreateShiftTypeRequest;
import com.example.hospitaljpa.dto.response.ShiftTypeResponse;
import com.example.hospitaljpa.service.ShiftTypeService;
import static com.example.hospitaljpa.util.ApiConstants.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(SHIFT_TYPES)
@Tag(name = "ShiftTypes", description = "Manage shift type lookup data")
@Slf4j
public class ShiftTypeController {

    private final ShiftTypeService service;

    public ShiftTypeController(ShiftTypeService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create shift type", description = "Create a new shift type used to categorize shifts")
    @ApiResponse(responseCode = "200", description = "Shift type created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShiftTypeResponse.class)))
    public ShiftTypeResponse create(@RequestBody CreateShiftTypeRequest request) {
        log.debug("POST {} - payload: {}", SHIFT_TYPES, request);
        var entity = service.create(request.getName());
        log.info("ShiftType created via controller id={}", entity.getId());
        return new ShiftTypeResponse(entity.getId(), entity.getName());
    }
}
