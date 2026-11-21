package com.college.controller;

import com.college.exception.DatabaseException;
import com.college.model.Course;
import com.college.service.CourseService;

import java.util.List;

public class CourseController {

    private final CourseService courseService = new CourseService();

    public boolean addCourse(String code, String name,
                           String department, int duration)
            throws DatabaseException {
        return courseService.addCourse(code, name, department, duration);
    }

    public List<Course> getAllCourses() throws DatabaseException {
        return courseService.getAllCourses();
    }

    public Course searchCourse(String code) throws DatabaseException {
        return courseService.searchCourse(code);
    }

    public boolean deleteCourse(String code) throws DatabaseException {
        return courseService.deleteCourse(code);
    }
}