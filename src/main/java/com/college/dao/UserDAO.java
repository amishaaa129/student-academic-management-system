package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;
import com.college.model.User;
import com.college.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Find user using username
    public User findByUsername(String username)
            throws DatabaseException {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        Role.valueOf(rs.getString("role")),
                        rs.getString("email")
                    );
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseException(
                "Error finding user", e
            );
        }
    }

    // Create a new user
    public void createUser(User user)
            throws DatabaseException {

        String sql = """
            INSERT INTO users
            (username, password_hash, role, email)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getEmail());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(
                "Error creating user", e
            );
        }
    }

    // Retrieve all users
    public List<User> findAll()
            throws DatabaseException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    Role.valueOf(rs.getString("role")),
                    rs.getString("email")
                );

                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new DatabaseException(
                "Error retrieving users", e
            );
        }
    }
}