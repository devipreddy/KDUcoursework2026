package com.example.securelock.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredAction {
    String value(); // e.g. "UNLOCK"
}
