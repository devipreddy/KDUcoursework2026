package com.smarthome.application.service;

import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.repository.SystemUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class SystemUserDetailsService implements UserDetailsService {

    private final SystemUserRepository repository;
    private static final Logger log = LoggerFactory.getLogger(SystemUserDetailsService.class);

    public SystemUserDetailsService(SystemUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Loading user by username: {}", username);
        SystemUser user = repository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found: {}", username);
            return new UsernameNotFoundException("User not found");
        });
        return new User(user.getUsername(), user.getPassword(), List.of());
    }
}
