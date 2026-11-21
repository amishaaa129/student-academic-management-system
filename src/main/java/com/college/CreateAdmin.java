package com.college;

import com.college.dao.UserDAO;
import com.college.model.User;
import com.college.model.Role;
import com.college.service.PasswordUtil;

public class CreateAdmin {

    public static void main(String[] args) {

        try {

            String hash = PasswordUtil.hashPassword("admin123");

            User admin = new User(
                0,
                "admin",
                hash,
                Role.ADMIN,
                "admin@college.com"
            );

            UserDAO dao = new UserDAO();

            dao.createUser(admin);

            System.out.println(
                "Admin account created successfully!"
            );

        } catch (Exception e) {
            System.out.println("Admin creation failed!");
            e.printStackTrace();
        }
    }
}