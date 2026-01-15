package com.example.securelock.aop;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
@Aspect
@Component
@Order(1)
public class SystemAlarmAspect {

    private static final Logger log = LoggerFactory.getLogger(SystemAlarmAspect.class);

    @AfterThrowing(
        pointcut = "execution(* com.example.securelock.service.SmartLockService.*(..))",
        throwing = "ex"
    )
    public void triggerAlarm(Exception ex) {

        log.error("ALARM TRIGGERED: System error detected: {}", ex.getMessage());
        callEmergencyService();
    }

    private void callEmergencyService() {
        log.error("Calling emergency response team ");
    }
}
