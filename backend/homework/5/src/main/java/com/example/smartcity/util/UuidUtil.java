package com.example.smartcity.util;

import java.util.UUID;

public final class UuidUtil {

    private UuidUtil() {}

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
