
package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;
import com.college.model.Student;
import com.college.model.Role;
import com.college.service.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // REGISTER STUDENT: create user + student profile atomically
    public boolean registerStudent(
            String username,
            String password,
            String email,
            String rollNumber,
            String fullName,
            String department,
            int semester) throws DatabaseException {

        System.out.println("DEBUG: StudentDAO.registerStudent() called");

        System.out.println("DEBUG: Attempting to insert user into database");

        String userSQL = "INSERT INTO users " +
                "(username, password_hash, role, email) " +
                "VALUES (?, ?, ?, ?)";

        System.out.println("DEBUG: Attempting to insert student profile");
        String studentSQL = "INSERT INTO students " +
                "(user_id, roll_number, full_name, department, semester) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Hash the password
            String passwordHash = PasswordUtil.hashPassword(password);

            // Insert into users table
            int userId;

            try (PreparedStatement userPS = conn.prepareStatement(
                    userSQL, Statement.RETURN_GENERATED_KEYS)) {

                userPS.setString(1, username);
                userPS.setString(2, passwordHash);
                userPS.setString(3, Role.STUDENT.name());
                userPS.setString(4, email);

                userPS.executeUpdate();

                try (ResultSet keys = userPS.getGeneratedKeys()) {

                    if (keys.next()) {
                        userId = keys.getInt(1);
                    } else {
                        throw new SQLException(
                                "Failed to retrieve generated user ID"
                        );
                    }
                }
            }

            // Insert into students table
            try (PreparedStatement studentPS =
                         conn.prepareStatement(studentSQL)) {

                studentPS.setInt(1, userId);
                studentPS.setString(2, rollNumber);
                studentPS.setString(3, fullName);
                studentPS.setString(4, department);
                studentPS.setInt(5, semester);

                studentPS.executeUpdate();
            }

            // Both insertions succeeded
            System.out.println("DEBUG: Transaction committing");
            conn.commit();

            return true;

        } catch (Exception e) {

            // Rollback if either insertion fails
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
            }

            throw new DatabaseException(
                    "Student registration failed: " + e.getMessage(), e
            );

        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // GET ALL STUDENTS
    public List<Student> getAllStudents() throws DatabaseException {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students ORDER BY student_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getInt("user_id"),
                        rs.getString("roll_number"),
                        rs.getString("full_name"),
                        rs.getString("department"),
                        rs.getInt("semester")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to retrieve students", e
            );
        }

        return students;
    }


    // SEARCH STUDENT
    public Student searchStudent(String rollNumber)
            throws DatabaseException {

        String sql = "SELECT * FROM students WHERE roll_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rollNumber);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Student(
                            rs.getInt("student_id"),
                            rs.getInt("user_id"),
                            rs.getString("roll_number"),
                            rs.getString("full_name"),
                            rs.getString("department"),
                            rs.getInt("semester")
                    );
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to search student", e
            );
        }

        return null;
    }


    // DELETE STUDENT
    public boolean deleteStudent(String rollNumber)
        throws DatabaseException {

    String sql = """
            DELETE FROM users
            WHERE user_id = (
                SELECT user_id
                FROM students
                WHERE roll_number = ?
            )
            AND role = 'STUDENT'
            """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, rollNumber);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        throw new DatabaseException(
                "Failed to delete student", e
        );
    }
}
}
