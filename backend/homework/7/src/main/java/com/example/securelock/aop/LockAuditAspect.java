package com.example.securelock.aop;

import com.example.securelock.config.SecurityContext;
import com.example.securelock.constants.AuditMessages;
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
import com.example.securelock.exception.AuthenticationException;

import java.time.LocalDateTime;

@Aspect
@Component
public class LockAuditAspect {

    private static final Logger log = LoggerFactory.getLogger(LockAuditAspect.class);

    private final AuthService authService;
    private final LockAccessLogRepository auditRepo;

    public LockAuditAspect(AuthService authService,
                           LockAccessLogRepository auditRepo) {
        this.authService = authService;
        this.auditRepo = auditRepo;
    }

    @Around("@annotation(com.example.securelock.aop.AccessAttempt)")
    public Object authenticateAndProceed(ProceedingJoinPoint pjp) throws Throwable {

        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) {
            throw new AuthenticationException("No HTTP context");
        }

        HttpServletRequest request = attrs.getRequest();

        String username = request.getHeader("X-USERNAME");
        String password = request.getHeader("X-PASSWORD");


        User user = authService.authenticate(username, password);
        SecurityContext.setUser(user);

        // Log + Persist ACCESS ATTEMPT
        log.info(String.format(AuditMessages.ACCESS_ATTEMPT, username));
        auditRepo.save(
                LockAccessLog.builder()
                        .username(username)
                        .action("UNLOCK")
                        .status("ATTEMPT")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        try {
            return pjp.proceed();
        } catch (Throwable ex) {
            auditRepo.save(
                    LockAccessLog.builder()
                            .username(username)
                            .action("UNLOCK")
                            .status("FAILED")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
            throw ex;
        }
    }

    @AfterReturning("@annotation(com.example.securelock.aop.AccessSuccess)")
    public void logSuccess() {

        User user = SecurityContext.getUser();

        log.info(String.format(AuditMessages.SUCCESS, user.getUsername()));

        auditRepo.save(
                LockAccessLog.builder()
                        .username(user.getUsername())
                        .action("UNLOCK")
                        .status("SUCCESS")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        SecurityContext.clear();
    }

    @AfterThrowing("@annotation(com.example.securelock.aop.AccessAttempt)")
    public void logFailure() {

        User user = SecurityContext.getUser();

        if (user != null) {
            log.warn(String.format(AuditMessages.FAILURE, user.getUsername()));

            auditRepo.save(
                    LockAccessLog.builder()
                            .username(user.getUsername())
                            .action("UNLOCK")
                            .status("FAILED")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }

        SecurityContext.clear();
    }

    @Around("@annotation(com.example.securelock.aop.Timed)")
    public Object time(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        log.info("Starting timed method: {}", pjp.getSignature().getName());

        Object result = pjp.proceed();   // business method executes here

        long end = System.currentTimeMillis();

        log.info("Finished timed method: {}", pjp.getSignature().getName());
        log.info("Execution time = {} ms", (end - start));

        return result;
    }



    @Around("@annotation(com.example.securelock.aop.PasscodeProtected)")
    public Object enforcePasscode(ProceedingJoinPoint pjp) throws Throwable {

        var user = SecurityContext.getUser();

        if (user == null) {
            log.warn("SECURITY ALERT: No authenticated user");
            return null;
        }

        if ("Unknown".equalsIgnoreCase(user.getUsername())) {
            log.error("SECURITY ALERT: Unauthorized access blocked!");
            return null; // DO NOT CALL PROCEED → method never runs
        }

        return pjp.proceed(); // allowed
    }


}
