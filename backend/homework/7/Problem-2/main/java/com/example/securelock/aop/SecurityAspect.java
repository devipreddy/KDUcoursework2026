package com.example.securelock.aop;

import com.example.securelock.config.SecurityContext;
import com.example.securelock.constants.AuditMessages;
import com.example.securelock.exception.AuthenticationException;
import com.example.securelock.model.LockAccessLog;
import com.example.securelock.model.User;
import com.example.securelock.repository.LockAccessLogRepository;
import com.example.securelock.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.*;

import java.time.LocalDateTime;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger log = LoggerFactory.getLogger(SecurityAspect.class);

    private final AuthService authService;
    private final LockAccessLogRepository auditRepo;

    public SecurityAspect(AuthService authService, LockAccessLogRepository auditRepo) {
        this.authService = authService;
        this.auditRepo = auditRepo;
    }

    @Around("@annotation(secured)")
    public Object secure(ProceedingJoinPoint pjp, SecuredAction secured) throws Throwable {

        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            throw new AuthenticationException("No HTTP context");
        }

        HttpServletRequest request = attrs.getRequest();
        String username = request.getHeader("X-USERNAME");
        String password = request.getHeader("X-PASSWORD");

        // 1️⃣ Always log ATTEMPT
        auditRepo.save(
                LockAccessLog.builder()
                        .username(username)
                        .action(secured.value())
                        .status("ATTEMPT")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        log.info(String.format(AuditMessages.ACCESS_ATTEMPT, username));

        try {
            // 2️⃣ Authenticate
            User user = authService.authenticate(username, password);
            SecurityContext.setUser(user);

            // 3️⃣ Authorize
            if ("Unknown".equalsIgnoreCase(user.getUsername())) {
                log.error("SECURITY ALERT: Unauthorized access blocked!");

                auditRepo.save(
                        LockAccessLog.builder()
                                .username(username)
                                .action(secured.value())
                                .status("FAILED")
                                .timestamp(LocalDateTime.now())
                                .build()
                );

                throw new AuthenticationException("Unauthorized access blocked");  // DO NOT CALL PROCEED
            }

            // 4️⃣ Execute business logic
            Object result = pjp.proceed();

            // 5️⃣ Success
            auditRepo.save(
                    LockAccessLog.builder()
                            .username(username)
                            .action(secured.value())
                            .status("SUCCESS")
                            .timestamp(LocalDateTime.now())
                            .build()
            );

            log.info(String.format(AuditMessages.SUCCESS, username));
            return result;

        } catch (Exception ex) {

            // 6️⃣ Failure
            auditRepo.save(
                    LockAccessLog.builder()
                            .username(username)
                            .action(secured.value())
                            .status("FAILED")
                            .timestamp(LocalDateTime.now())
                            .build()
            );

            log.warn(String.format(AuditMessages.FAILURE, username));
            throw ex;
        }
        finally {
            SecurityContext.clear();
        }
    }
}
