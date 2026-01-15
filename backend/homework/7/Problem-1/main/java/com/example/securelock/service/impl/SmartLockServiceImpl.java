package com.example.securelock.service.impl;

import com.example.securelock.aop.AccessAttempt;
import com.example.securelock.aop.AccessSuccess;
import com.example.securelock.service.SmartLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmartLockServiceImpl implements SmartLockService {

    private static final Logger log = LoggerFactory.getLogger(SmartLockServiceImpl.class);

    @Override
    @AccessAttempt
    @AccessSuccess
    public void unlock() {
        log.info("The door is now open");
    }
}
