package com.example.eventsphere.service;

import com.example.eventsphere.entity.TicketUser;
import com.example.eventsphere.repository.TicketUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log =
            LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final TicketUserRepository userRepository;

    public CustomUserDetailsService(TicketUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        TicketUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Authentication failed for unknown user: {}", username);
                    return new UsernameNotFoundException(
                            "User not found: " + username);
                });

        String role = "ROLE_" + user.getRole();

        return new User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
