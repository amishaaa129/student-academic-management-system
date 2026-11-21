package com.college.controller;

import com.college.service.AuthService;
import com.college.model.User;
import com.college.exception.*;

public class LoginController {

    private final AuthService authService;

    public LoginController() {
        authService = new AuthService();
    }

    public User authenticate(
            String username, String password)
            throws AuthenticationException,
                   DatabaseException {

        return authService.login(username, password);
    }

    public String getDashboard(User user) {

        return authService.getDashboard(user.getRole());
    }
}