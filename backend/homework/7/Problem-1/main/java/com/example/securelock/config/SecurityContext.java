package com.example.securelock.config;

import com.example.securelock.model.User;

public final class SecurityContext {

    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<>();

    private SecurityContext() {}

    public static void setUser(User user) {
        CURRENT_USER.set(user);
    }

    public static User getUser() {
        return CURRENT_USER.get();
    }

    public static void clear() {
        CURRENT_USER.remove();
    }
}
