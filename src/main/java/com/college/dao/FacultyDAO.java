package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;
import com.college.model.Faculty;
import com.college.model.Role;
import com.college.service.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO {

    public boolean registerFaculty(
            String username,
            String password,
            String email,
            String facultyCode,
            String fullName,
            String department,
            String designation
    ) throws DatabaseException {

        String userSQL = """
                INSERT INTO users(username, password_hash, role, email)
                VALUES (?, ?, ?, ?)
                """;

        String facultySQL = """
                INSERT INTO faculty(
                    user_id, faculty_code, full_name,
                    department, designation
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String hashedPassword = PasswordUtil.hashPassword(password);

            int userId;

            try (PreparedStatement ps = conn.prepareStatement(
                    userSQL, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                ps.setString(3, Role.FACULTY.name());
                ps.setString(4, email);

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("User ID was not generated.");
                    }

                    userId = keys.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(facultySQL)) {

                ps.setInt(1, userId);
                ps.setString(2, facultyCode);
                ps.setString(3, fullName);
                ps.setString(4, department);
                ps.setString(5, designation);

                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
            }

            throw new DatabaseException(
                    "Faculty registration failed: " + e.getMessage(), e
            );

        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    public List<Faculty> getAllFaculty() throws DatabaseException {

        String sql = """
                SELECT faculty_id, user_id, faculty_code,
                       full_name, department, designation
                FROM faculty
                ORDER BY faculty_id
                """;

        List<Faculty> facultyList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                facultyList.add(new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getInt("user_id"),
                        rs.getString("faculty_code"),
                        rs.getString("full_name"),
                        rs.getString("department"),
                        rs.getString("designation")
                ));
            }

            return facultyList;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to retrieve faculty", e
            );
        }
    }

    public Faculty searchFaculty(String facultyCode)
            throws DatabaseException {

        String sql = """
                SELECT faculty_id, user_id, faculty_code,
                       full_name, department, designation
                FROM faculty
                WHERE faculty_code = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facultyCode);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Faculty(
                            rs.getInt("faculty_id"),
                            rs.getInt("user_id"),
                            rs.getString("faculty_code"),
                            rs.getString("full_name"),
                            rs.getString("department"),
                            rs.getString("designation")
                    );
                }

                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to search faculty", e
            );
        }
    }

    public boolean deleteFaculty(String facultyCode)
            throws DatabaseException {

        String sql = """
                DELETE FROM users
                WHERE user_id = (
                    SELECT user_id FROM faculty
                    WHERE faculty_code = ?
                )
                AND role = 'FACULTY'
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, facultyCode);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to delete faculty", e
            );
        }
    }
}