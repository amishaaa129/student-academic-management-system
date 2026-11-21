
package com.college.service;

import com.college.dao.StudentDAO;
import com.college.exception.DatabaseException;
import com.college.model.Student;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();

    // REGISTER STUDENT
    public boolean registerStudent(
            String username,
            String password,
            String email,
            String rollNumber,
            String fullName,
            String department,
            int semester) throws DatabaseException {

                System.out.println("DEBUG: StudentService.registerStudent() called");

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username is required");

        if (password == null || password.length() < 6)
            throw new IllegalArgumentException(
                    "Password must contain at least 6 characters"
            );

        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is required");

        if (rollNumber == null || rollNumber.isBlank())
            throw new IllegalArgumentException("Roll number is required");

        if (fullName == null || fullName.isBlank())
            throw new IllegalArgumentException("Full name is required");

        if (department == null || department.isBlank())
            throw new IllegalArgumentException("Department is required");

        if (semester < 1 || semester > 8)
            throw new IllegalArgumentException(
                    "Semester must be between 1 and 8"
            );

        System.out.println("DEBUG: Validation passed");

        return studentDAO.registerStudent(
                username,
                password,
                email,
                rollNumber,
                fullName,
                department,
                semester
        );
    }


    public List<Student> getAllStudents() throws DatabaseException {
        return studentDAO.getAllStudents();
    }


    public Student searchStudent(String rollNumber)
            throws DatabaseException {

        if (rollNumber == null || rollNumber.isBlank())
            throw new IllegalArgumentException(
                    "Roll number is required"
            );

        return studentDAO.searchStudent(rollNumber);
    }


    public boolean deleteStudent(String rollNumber)
            throws DatabaseException {

        if (rollNumber == null || rollNumber.isBlank())
            throw new IllegalArgumentException(
                    "Roll number is required"
            );

        return studentDAO.deleteStudent(rollNumber);
    }
}
