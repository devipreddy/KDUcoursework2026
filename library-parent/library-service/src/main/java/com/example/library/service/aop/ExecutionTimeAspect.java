package com.example.library.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    private static final Logger log =
            LoggerFactory.getLogger("EXECUTION_TIME");

    /**
     * Intercepts all public methods in library-service
     */
    @Around("execution(public * com.example.library.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        try {
            return pjp.proceed();
        } finally {
            long durationMs = System.currentTimeMillis() - start;

            log.info(
                "EXECUTION_TIME method={} durationMs={}",
                pjp.getSignature().toShortString(),
                durationMs
            );
        }
    }
}
