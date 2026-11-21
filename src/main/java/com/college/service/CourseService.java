package com.college.service;

import com.college.dao.CourseDAO;
import com.college.exception.DatabaseException;
import com.college.model.Course;

import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO = new CourseDAO();

    public boolean addCourse(String code, String name,
                           String department, int duration)
            throws DatabaseException {

        if (code == null || code.isBlank())
            throw new IllegalArgumentException("Course code required.");

        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Course name required.");

        if (department == null || department.isBlank())
            throw new IllegalArgumentException("Department required.");

        if (duration < 1 || duration > 10)
            throw new IllegalArgumentException("Invalid course duration.");

        return courseDAO.addCourse(code, name, department, duration);
    }

    public List<Course> getAllCourses() throws DatabaseException {
        return courseDAO.getAllCourses();
    }

    public Course searchCourse(String code) throws DatabaseException {
        return courseDAO.searchCourse(code);
    }

    public boolean deleteCourse(String code) throws DatabaseException {
        return courseDAO.deleteCourse(code);
    }
}