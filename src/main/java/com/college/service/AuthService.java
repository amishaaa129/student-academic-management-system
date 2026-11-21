package com.college.service;

import com.college.dao.UserDAO;
import com.college.exception.*;
import com.college.model.User;
import com.college.model.Role;
import com.college.util.AppLogger;

import java.util.Map;
import java.util.HashMap;

public class AuthService {

    private final UserDAO userDAO;

    // Collections and Generics
    private final Map<Role, String> roleDescriptions =
        new HashMap<>();

    public AuthService() {

        userDAO = new UserDAO();

        roleDescriptions.put(
            Role.ADMIN, "Administrator Dashboard"
        );

        roleDescriptions.put(
            Role.FACULTY, "Faculty Dashboard"
        );

        roleDescriptions.put(
            Role.STUDENT, "Student Dashboard"
        );
    }

    public User login(String username, String password)
            throws AuthenticationException,
                   DatabaseException {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            throw new AuthenticationException(
                "Username and password cannot be empty"
            );
        }

        User user = userDAO.findByUsername(username);

        if (user == null) {

            AppLogger.info(
                "Failed login attempt for username: " + username
            );

            throw new AuthenticationException(
                "Invalid username or password"
            );
        }

        try {

            boolean valid = PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash()
            );

            if (!valid) {

                AppLogger.info(
                    "Failed login attempt for username: " + username
                );

                throw new AuthenticationException(
                    "Invalid username or password"
                );
            }

            AppLogger.info(
                "Successful login: " + username
            );

            return user;

        } catch (AuthenticationException e) {
            throw e;

        } catch (Exception e) {

            AppLogger.error(
                "Password verification failed", e
            );

            throw new AuthenticationException(
                "Authentication could not be completed"
            );
        }
    }

    public String getDashboard(Role role) {

        return roleDescriptions.get(role);
    }
}