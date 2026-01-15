package com.example.securelock.constants;

public final class ApiPaths {

    private ApiPaths() {}

    public static final String API_BASE = "/api";

    // Auth
    public static final String AUTH_BASE = API_BASE + "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";

    // Lock
    public static final String LOCK_BASE = API_BASE + "/lock";
    public static final String UNLOCK = "/unlock";
}
