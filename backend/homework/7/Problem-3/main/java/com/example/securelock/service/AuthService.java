package com.example.securelock.service;

import com.example.securelock.model.User;

public interface AuthService {

    User register(String username, String password);

    User authenticate(String username, String password);
}
