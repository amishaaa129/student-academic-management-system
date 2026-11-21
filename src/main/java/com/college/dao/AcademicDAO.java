package com.college.dao;

import com.college.config.DBConnection;
import com.college.exception.DatabaseException;

import java.sql.*;

public class AcademicDAO {

    // Assign a faculty member to a subject
    public boolean assignFaculty(int facultyId, int subjectId,
                               String academicYear)
            throws DatabaseException {

        String sql = """
                INSERT INTO faculty_subjects
                (faculty_id, subject_id, academic_year)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, facultyId);
            ps.setInt(2, subjectId);
            ps.setString(3, academicYear);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Faculty assignment failed: " + e.getMessage(), e
            );
        }
    }

    // Register a student for a subject
    public boolean registerStudentSubject(
            int studentId, int subjectId, String academicYear)
            throws DatabaseException {

        String sql = """
                INSERT INTO student_subjects
                (student_id, subject_id, academic_year)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setString(3, academicYear);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Subject registration failed: " + e.getMessage(), e
            );
        }
    }

    // Add a timetable entry
    public boolean addTimetableEntry(
            int subjectId,
            int facultyId,
            String day,
            String startTime,
            String endTime,
            String room)
            throws DatabaseException {

        String sql = """
                INSERT INTO timetable(
                    subject_id, faculty_id, day_of_week,
                    start_time, end_time, room_number
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, subjectId);
            ps.setInt(2, facultyId);
            ps.setString(3, day.toUpperCase());
            ps.setTime(4, Time.valueOf(startTime));
            ps.setTime(5, Time.valueOf(endTime));
            ps.setString(6, room);

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            throw new DatabaseException(
                    "Timetable creation failed: " + e.getMessage(), e
            );
        }
    }

    // Post a notice
    public boolean postNotice(String title, String content, int userId)
            throws DatabaseException {

        String sql = """
                INSERT INTO notices(title, content, posted_by)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Notice creation failed: " + e.getMessage(), e
            );
        }
    }

    public void viewTimetable() throws DatabaseException {

        String sql = """
                SELECT t.timetable_id, s.subject_name,
                    f.full_name, t.day_of_week,
                    t.start_time, t.end_time, t.room_number
                FROM timetable t
                JOIN subjects s ON t.subject_id = s.subject_id
                JOIN faculty f ON t.faculty_id = f.faculty_id
                ORDER BY t.day_of_week, t.start_time
                """;

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== TIMETABLE ==========");

            while (rs.next()) {

                System.out.println("\nTimetable ID: "
                        + rs.getInt("timetable_id"));

                System.out.println("Subject: "
                        + rs.getString("subject_name"));

                System.out.println("Faculty: "
                        + rs.getString("full_name"));

                System.out.println("Day: "
                        + rs.getString("day_of_week"));

                System.out.println("Time: "
                        + rs.getTime("start_time") + " - "
                        + rs.getTime("end_time"));

                System.out.println("Room: "
                        + rs.getString("room_number"));
            }

        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to display timetable", e
            );
        }
    }
}