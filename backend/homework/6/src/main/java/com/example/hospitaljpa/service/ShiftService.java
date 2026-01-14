package com.example.hospitaljpa.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.example.hospitaljpa.dto.request.CreateShiftRequest;
import com.example.hospitaljpa.dto.response.ShiftResponse;
import com.example.hospitaljpa.entities.ShiftEntity;
import com.example.hospitaljpa.entities.ShiftTypeEntity;
import com.example.hospitaljpa.exception.NotFoundException;
import com.example.hospitaljpa.repo.ShiftRepository;
import com.example.hospitaljpa.repo.ShiftTypeRepository;
import com.example.hospitaljpa.util.ErrorCodes;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Service layer handling business logic related to shifts. Adds structured
 * logging around important operations to aid debugging and production
 * observability.
 */
@Service
@Slf4j
public class ShiftService {

    private final ShiftRepository shiftRepo;
    private final ShiftTypeRepository typeRepo;

    public ShiftService(ShiftRepository shiftRepo, ShiftTypeRepository typeRepo) {
        this.shiftRepo = shiftRepo;
        this.typeRepo = typeRepo;
    }

    public ShiftEntity create(CreateShiftRequest request) {
        log.debug("Creating shift with payload: {}", request);

        // Validate ShiftType exists
        ShiftTypeEntity type = typeRepo.findById(request.getShiftTypeId())
                .orElseThrow(() -> {
                    log.warn("ShiftType not found: id={}", request.getShiftTypeId());
                    return new NotFoundException(
                            ErrorCodes.SHIFT_TYPE_NOT_FOUND,
                            "ShiftType " + request.getShiftTypeId() + " not found"
                    );
                });

        // Create Shift
        ShiftEntity shift = new ShiftEntity();
        shift.setShiftName(request.getShiftName());
        shift.setStartDate(request.getStartDate());
        shift.setEndDate(request.getEndDate());
        shift.setShiftType(type);

        ShiftEntity saved = shiftRepo.save(shift);
        log.info("Created shift id={} name={}", saved.getId(), saved.getShiftName());
        return saved;
    }

    public ShiftEntity get(Long id) {
        log.debug("Fetching shift id={}", id);
        // Validate Shift exists
        return shiftRepo.findById(id)
                .orElseThrow(() -> {
                    log.warn("Shift not found: id={}", id);
                    return new NotFoundException(ErrorCodes.SHIFT_NOT_FOUND, "Shift " + id + " not found");
                });
    }

    public List<ShiftResponse> getTop3NewYearShifts() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 25);
        log.debug("Querying top3 New Year shifts between {} and {}", start, end);

        var shifts = shiftRepo.findTop3NewYearShifts(start, end, PageRequest.of(0, 3));

        var responses = shifts.stream()
                .map(s -> new ShiftResponse(
                        s.getId(),
                        s.getShiftName(),
                        s.getStartDate(),
                        s.getEndDate(),
                        s.getShiftType().getName()
                ))
                .toList();

        log.info("Top3 New Year shifts returned: count={}", responses.size());
        return responses;

    }
}
