
package com.college.controller;

import com.college.model.Student;
import com.college.service.StudentService;
import com.college.exception.DatabaseException;

import java.util.List;

public class StudentController {

    private final StudentService studentService =
            new StudentService();


    // REGISTER STUDENT
    public boolean registerStudent(
        String username,
        String password,
        String email,
        String rollNumber,
        String fullName,
        String department,
        int semester) throws DatabaseException {

    System.out.println("DEBUG: StudentController.registerStudent() called");

    return studentService.registerStudent(
            username, password, email, rollNumber,
            fullName, department, semester
    );
}


    public List<Student> getAllStudents()
            throws DatabaseException {

        return studentService.getAllStudents();
    }


    public Student searchStudent(String rollNumber)
            throws DatabaseException {

        return studentService.searchStudent(rollNumber);
    }


    public boolean deleteStudent(String rollNumber)
            throws DatabaseException {

        return studentService.deleteStudent(rollNumber);
    }
}
