package com.example.securelock.service.impl;

import com.example.securelock.aop.SecuredAction;
import com.example.securelock.aop.Timed;
import com.example.securelock.service.SmartLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmartLockServiceImpl implements SmartLockService {

    private static final Logger log = LoggerFactory.getLogger(SmartLockServiceImpl.class);

    // @Override
    // @SecuredAction("UNLOCK")
    // @Timed
    // public void unlock() {
    //     log.info("The door is now open");
    // }

    // @Override
    // @Timed
    // public void checkBattery() {
    //     try {
    //         Thread.sleep(300); // simulate real hardware check
    //     } catch (InterruptedException e) {
    //         Thread.currentThread().interrupt();
    //     }
    //     log.info("Battery level is OK");
    // }

    @Override
    @SecuredAction("UNLOCK")
    @Timed
    public void unlock(String input) {
        log.info("The door is now open with input: {}", input);
    }
}
