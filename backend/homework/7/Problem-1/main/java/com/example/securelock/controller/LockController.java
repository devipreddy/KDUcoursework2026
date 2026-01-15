package com.example.securelock.controller;

import com.example.securelock.constants.ApiPaths;
import com.example.securelock.dto.UnlockResponseDTO;
import com.example.securelock.service.SmartLockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.LOCK_BASE)
public class LockController {

    private final SmartLockService smartLockService;

    public LockController(SmartLockService smartLockService) {
        this.smartLockService = smartLockService;
    }

    @PostMapping(ApiPaths.UNLOCK)
    public UnlockResponseDTO unlock() {
        smartLockService.unlock();
        return new UnlockResponseDTO("Door unlocked");
    }
}
