package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;
import com.college.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public boolean addCourse(String code, String name,
                           String department, int duration)
            throws DatabaseException {

        String sql = """
                INSERT INTO courses(course_code, course_name,
                                   department, duration_years)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setInt(4, duration);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to add course: " + e.getMessage(), e
            );
        }
    }

    public List<Course> getAllCourses() throws DatabaseException {

        List<Course> courses = new ArrayList<>();

        String sql = "SELECT * FROM courses ORDER BY course_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        rs.getString("department"),
                        rs.getInt("duration_years")
                ));
            }

            return courses;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to retrieve courses", e
            );
        }
    }

    public Course searchCourse(String code)
            throws DatabaseException {

        String sql = "SELECT * FROM courses WHERE course_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_code"),
                            rs.getString("course_name"),
                            rs.getString("department"),
                            rs.getInt("duration_years")
                    );
                }

                return null;
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to search course", e
            );
        }
    }

    public boolean deleteCourse(String code)
            throws DatabaseException {

        String sql = "DELETE FROM courses WHERE course_code = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, code);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to delete course", e
            );
        }
    }
}