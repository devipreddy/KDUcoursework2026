package com.example.hospitaljpa.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.hospitaljpa.dto.response.UserResponse;
import com.example.hospitaljpa.entities.UserEntity;
import com.example.hospitaljpa.exception.NotFoundException;
import com.example.hospitaljpa.repo.UserRepository;
import org.springframework.data.domain.Page;
import com.example.hospitaljpa.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {


    private static final int MAX_SIZE = 50;

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserEntity create(String name, String role) {
        log.debug("Creating user name={} role={}", name, role);

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setRole(role);
        UserEntity saved = repo.save(user);
        log.info("Created user id={} name={}", saved.getId(), saved.getName());
        return saved;
    }

    public Page<UserResponse> getUsers(int page, int size) {

        log.debug("Listing users page={} size={}", page, size);
        //Validate page and size
        if (size < 1 || size > MAX_SIZE) {
            log.warn("Invalid page size requested: {}", size);
            throw new ValidationException("Size must be between 1 and 50");
        }
        
        //Fetch paginated users
        return repo.findAll(PageRequest.of(page, size))
                .map(this::toDto);
    }

    private UserResponse toDto(UserEntity u) {
        return new UserResponse(u.getId(), u.getName(), u.getRole());
    }

    public UserEntity get(Long id) {
        log.debug("Fetching user id={}", id);
        //Validate User exists
        return repo.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found: id={}", id);
                    return new NotFoundException("USER_NOT_FOUND", "User " + id + " not found");
                });
    }
}
