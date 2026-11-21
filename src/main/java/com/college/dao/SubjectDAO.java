package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;
import com.college.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public boolean addSubject(int courseId, String code,
                            String name, int semester, int credits)
            throws DatabaseException {

        String sql = """
                INSERT INTO subjects(
                    course_id, subject_code, subject_name,
                    semester, credits
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.setString(2, code);
            ps.setString(3, name);
            ps.setInt(4, semester);
            ps.setInt(5, credits);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to add subject: " + e.getMessage(), e
            );
        }
    }

    public List<Subject> getAllSubjects() throws DatabaseException {

        List<Subject> subjects = new ArrayList<>();

        String sql = """
                SELECT * FROM subjects
                ORDER BY course_id, semester
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                subjects.add(new Subject(
                        rs.getInt("subject_id"),
                        rs.getInt("course_id"),
                        rs.getString("subject_code"),
                        rs.getString("subject_name"),
                        rs.getInt("semester"),
                        rs.getInt("credits")
                ));
            }

            return subjects;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to retrieve subjects", e
            );
        }
    }

    public Subject searchSubject(String code)
            throws DatabaseException {

        String sql = "SELECT * FROM subjects WHERE subject_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Subject(
                            rs.getInt("subject_id"),
                            rs.getInt("course_id"),
                            rs.getString("subject_code"),
                            rs.getString("subject_name"),
                            rs.getInt("semester"),
                            rs.getInt("credits")
                    );
                }

                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to search subject", e
            );
        }
    }

    public boolean deleteSubject(String code)
            throws DatabaseException {

        String sql = "DELETE FROM subjects WHERE subject_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to delete subject", e
            );
        }
    }
}