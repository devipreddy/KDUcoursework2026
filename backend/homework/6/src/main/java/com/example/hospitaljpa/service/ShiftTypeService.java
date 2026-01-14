package com.example.hospitaljpa.service;

import org.springframework.stereotype.Service;
import com.example.hospitaljpa.entities.ShiftTypeEntity;
import com.example.hospitaljpa.exception.NotFoundException;
import com.example.hospitaljpa.repo.ShiftTypeRepository;
import static com.example.hospitaljpa.util.ErrorCodes.*;
import lombok.extern.slf4j.Slf4j;

/** Service for managing shift types with structured logging. */
@Service
@Slf4j
public class ShiftTypeService {

    private final ShiftTypeRepository repo;

    public ShiftTypeService(ShiftTypeRepository repo) {
        this.repo = repo;
    }

    public ShiftTypeEntity create(String name) {
        log.debug("Creating ShiftType name={}", name);
        ShiftTypeEntity type = new ShiftTypeEntity();
        type.setName(name);
        ShiftTypeEntity saved = repo.save(type);
        log.info("Created ShiftType id={} name={}", saved.getId(), saved.getName());
        return saved;
    }

    public ShiftTypeEntity get(Long id) {
        log.debug("Fetching ShiftType id={}", id);
        return repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("ShiftType not found: id={}", id);
                    return new NotFoundException(SHIFT_TYPE_NOT_FOUND, "ShiftType " + id + " does not exist");
                });
    }
}
