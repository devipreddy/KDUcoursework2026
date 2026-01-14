package com.example.hospitaljpa.service;

import org.springframework.stereotype.Service;
import com.example.hospitaljpa.entities.*;
import com.example.hospitaljpa.exception.NotFoundException;
import com.example.hospitaljpa.repo.*;
import static com.example.hospitaljpa.util.ErrorCodes.*;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShiftUserService {

    private final ShiftUserRepository repo;
    private final ShiftRepository shiftRepo;
    private final UserRepository userRepo;

    public ShiftUserService(
            ShiftUserRepository repo,
            ShiftRepository shiftRepo,
            UserRepository userRepo) {
        this.repo = repo;
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
    }

    public ShiftUserEntity assign(Long shiftId, Long userId) {
        log.debug("Assigning user {} to shift {}", userId, shiftId);

        // Validate Shift exists
        ShiftEntity shift = shiftRepo.findById(shiftId)
                .orElseThrow(() -> {
                    log.warn("Shift not found for assignment: id={}", shiftId);
                    return new NotFoundException(SHIFT_NOT_FOUND, "Shift " + shiftId + " does not exist");
                });

        // Validate User exists
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found for assignment: id={}", userId);
                    return new NotFoundException(USER_NOT_FOUND, "User " + userId + " does not exist");
                });

        // Create Assignment
        ShiftUserEntity assignment = new ShiftUserEntity();
        assignment.setShift(shift);
        assignment.setUser(user);

        ShiftUserEntity saved = repo.save(assignment);
        log.info("Created assignment id={} shiftId={} userId={}", saved.getId(), shiftId, userId);
        return saved;
    }
}

