
package com.college;

import com.college.controller.LoginController;
import com.college.controller.StudentController;
import com.college.controller.FacultyController;
import com.college.controller.CourseController;
import com.college.controller.SubjectController;
import com.college.controller.AcademicController;

import com.college.exception.AuthenticationException;
import com.college.exception.DatabaseException;

import com.college.model.User;
import com.college.model.Role;
import com.college.model.Student;
import com.college.model.Faculty;
import com.college.model.Course;
import com.college.model.Subject;
import com.college.model.Notice;

import com.college.dao.NoticeDAO;
import com.college.util.AppLogger;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginController controller = new LoginController();

        boolean running = true;

        while (running) {

            System.out.println("\n====================================");
            System.out.println(" STUDENT ACADEMIC MANAGEMENT SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> {

                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        try {

                            User user = controller.authenticate(
                                    username, password
                            );

                            System.out.println("\nLogin successful!");
                            System.out.println(
                                    "Welcome, " + user.getUsername()
                            );

                            System.out.println(
                                    controller.getDashboard(user)
                            );

                            switch (user.getRole()) {

                                case ADMIN ->
                                        adminDashboard(sc, user);

                                case FACULTY ->
                                        facultyDashboard(sc, user);

                                case STUDENT ->
                                        studentDashboard(sc, user);
                            }

                        } catch (AuthenticationException e) {

                            System.out.println(
                                    "Login failed: " + e.getMessage()
                            );

                        } catch (DatabaseException e) {

                            System.out.println(
                                    "Database error: " + e.getMessage()
                            );

                            AppLogger.error(
                                    "Login database error", e
                            );
                        }
                    }

                    case 2 -> {

                        running = false;

                        System.out.println(
                                "Thank you for using the system!"
                        );
                    }

                    default -> System.out.println(
                            "Invalid choice. Try again."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }

        sc.close();
    }


    // ================= ADMIN DASHBOARD =================

    private static void adminDashboard(Scanner sc, User user) {

        while (true) {

            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Faculty");
            System.out.println("3. Manage Courses");
            System.out.println("4. Manage Subjects");
            System.out.println("5. Academic Operations");
            System.out.println("6. View Timetable");
            System.out.println("7. View Notices");
            System.out.println("8. Logout");

            System.out.print("Choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> studentManagementMenu(sc);

                    case 2 -> facultyManagementMenu(sc);

                    case 3 -> courseManagementMenu(sc);

                    case 4 -> subjectManagementMenu(sc);

                    case 5 -> academicOperationsMenu(sc, user);

                    case 6 -> {
                        AcademicController controller =
                                new AcademicController();

                        controller.viewTimetable();
                    }

                    case 7 -> displayNotices();

                    case 8 -> {
                        System.out.println("Logging out...");
                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (Exception e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );

                AppLogger.error(
                        "Admin dashboard error", e
                );
            }
        }
    }


    // ================= STUDENT MANAGEMENT =================

    private static void studentManagementMenu(Scanner sc) {

        StudentController controller = new StudentController();

        while (true) {

            System.out.println("\n====================================");
            System.out.println("        STUDENT MANAGEMENT");
            System.out.println("====================================");

            System.out.println("1. Register Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Back to Admin Dashboard");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    // REGISTER STUDENT
                    case 1 -> {

                        System.out.println(
                                "\n----- REGISTER STUDENT -----"
                        );

                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Roll Number: ");
                        String rollNumber = sc.nextLine();

                        System.out.print("Full Name: ");
                        String fullName = sc.nextLine();

                        System.out.print("Department: ");
                        String department = sc.nextLine();

                        System.out.print("Semester: ");
                        int semester = Integer.parseInt(
                                sc.nextLine()
                        );

                        boolean registered =
                                controller.registerStudent(
                                        username,
                                        password,
                                        email,
                                        rollNumber,
                                        fullName,
                                        department,
                                        semester
                                );

                        if (registered) {

                            System.out.println(
                                    "\nStudent registered successfully!"
                            );

                            System.out.println(
                                    "Student can now log in using the credentials."
                            );
                        }
                    }

                    // VIEW ALL STUDENTS
                    case 2 -> {

                        System.out.println(
                                "\n----- ALL STUDENTS -----"
                        );

                        List<Student> students =
                                controller.getAllStudents();

                        if (students.isEmpty()) {

                            System.out.println(
                                    "No students found."
                            );

                        } else {

                            students.forEach(System.out::println);
                        }
                    }

                    // SEARCH STUDENT
                    case 3 -> {

                        System.out.println(
                                "\n----- SEARCH STUDENT -----"
                        );

                        System.out.print(
                                "Enter roll number: "
                        );

                        String searchRoll = sc.nextLine();

                        Student student =
                                controller.searchStudent(
                                        searchRoll
                                );

                        if (student != null) {

                            System.out.println("\nStudent found!");
                            System.out.println(student);

                        } else {

                            System.out.println(
                                    "Student not found."
                            );
                        }
                    }

                    // DELETE STUDENT
                    case 4 -> {

                        System.out.println(
                                "\n----- DELETE STUDENT -----"
                        );

                        System.out.print(
                                "Enter roll number: "
                        );

                        String deleteRoll = sc.nextLine();

                        boolean deleted =
                                controller.deleteStudent(
                                        deleteRoll
                                );

                        if (deleted) {

                            System.out.println(
                                    "Student deleted successfully!"
                            );

                        } else {

                            System.out.println(
                                    "Student not found."
                            );
                        }
                    }

                    // BACK
                    case 5 -> {
                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice. Try again."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid number entered."
                );

            } catch (Exception e) {

                System.out.println(
                        "\nOperation failed: " + e.getMessage()
                );

                AppLogger.error(
                        "Student management operation failed", e
                );
            }
        }
    }


    // ================= FACULTY DASHBOARD =================

    private static void facultyDashboard(
            Scanner sc, User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n====================================");
            System.out.println("         FACULTY DASHBOARD");
            System.out.println("====================================");

            System.out.println(
                    "Welcome, " + user.getUsername()
            );

            System.out.println();

            System.out.println("1. View My Subjects");
            System.out.println("2. Mark Attendance");
            System.out.println("3. Upload Marks");
            System.out.println("4. Manage Assignments");
            System.out.println("5. View Notices");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> System.out.println(
                            "Subject module coming soon!"
                    );

                    case 2 -> System.out.println(
                            "Attendance module coming soon!"
                    );

                    case 3 -> System.out.println(
                            "Marks module coming soon!"
                    );

                    case 4 -> System.out.println(
                            "Assignment module coming soon!"
                    );

                    case 5 -> displayNotices();

                    case 6 -> {

                        loggedIn = false;

                        System.out.println(
                                "Faculty logged out successfully."
                        );
                    }

                    default -> System.out.println(
                            "Invalid choice. Try again."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // ================= STUDENT DASHBOARD =================

    private static void studentDashboard(
            Scanner sc, User user) {

        boolean loggedIn = true;

        while (loggedIn) {

            System.out.println("\n====================================");
            System.out.println("         STUDENT DASHBOARD");
            System.out.println("====================================");

            System.out.println(
                    "Welcome, " + user.getUsername()
            );

            System.out.println();

            System.out.println("1. View My Attendance");
            System.out.println("2. View My Marks");
            System.out.println("3. View My Timetable");
            System.out.println("4. View Assignments");
            System.out.println("5. View Notices");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> System.out.println(
                            "Attendance module coming soon!"
                    );

                    case 2 -> System.out.println(
                            "Marks module coming soon!"
                    );

                    case 3 -> System.out.println(
                            "Timetable module coming soon!"
                    );

                    case 4 -> System.out.println(
                            "Assignment module coming soon!"
                    );

                    case 5 -> displayNotices();

                    case 6 -> {

                        loggedIn = false;

                        System.out.println(
                                "Student logged out successfully."
                        );
                    }

                    default -> System.out.println(
                            "Invalid choice. Try again."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }


    // ================= FACULTY MANAGEMENT =================

    private static void facultyManagementMenu(Scanner sc) {

        FacultyController controller =
                new FacultyController();

        while (true) {

            System.out.println("\n===== FACULTY MANAGEMENT =====");
            System.out.println("1. Register Faculty");
            System.out.println("2. View All Faculty");
            System.out.println("3. Search Faculty");
            System.out.println("4. Delete Faculty");
            System.out.println("5. Back");

            System.out.print("Choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> {

                        System.out.print("Username: ");
                        String username = sc.nextLine();

                        System.out.print("Password: ");
                        String password = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Faculty Code: ");
                        String code = sc.nextLine();

                        System.out.print("Full Name: ");
                        String name = sc.nextLine();

                        System.out.print("Department: ");
                        String department = sc.nextLine();

                        System.out.print("Designation: ");
                        String designation = sc.nextLine();

                        boolean result =
                                controller.registerFaculty(
                                        username,
                                        password,
                                        email,
                                        code,
                                        name,
                                        department,
                                        designation
                                );

                        if (result) {

                            System.out.println(
                                    "Faculty registered successfully!"
                            );
                        }
                    }

                    case 2 -> {

                        List<Faculty> faculty =
                                controller.getAllFaculty();

                        if (faculty.isEmpty()) {

                            System.out.println(
                                    "No faculty found."
                            );

                        } else {

                            faculty.forEach(System.out::println);
                        }
                    }

                    case 3 -> {

                        System.out.print(
                                "Enter Faculty Code: "
                        );

                        String code = sc.nextLine();

                        Faculty f =
                                controller.searchFaculty(code);

                        if (f != null) {

                            System.out.println(f);

                        } else {

                            System.out.println(
                                    "Faculty not found."
                            );
                        }
                    }

                    case 4 -> {

                        System.out.print(
                                "Enter Faculty Code to delete: "
                        );

                        String code = sc.nextLine();

                        if (controller.deleteFaculty(code)) {

                            System.out.println(
                                    "Faculty deleted."
                            );

                        } else {

                            System.out.println(
                                    "Faculty not found."
                            );
                        }
                    }

                    case 5 -> {
                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (Exception e) {

                System.out.println(
                        "Operation failed: " + e.getMessage()
                );

                AppLogger.error(
                        "Faculty management failed", e
                );
            }
        }
    }


    // ================= COURSE MANAGEMENT =================

    private static void courseManagementMenu(Scanner sc) {

        CourseController controller =
                new CourseController();

        while (true) {

            System.out.println("\n===== COURSE MANAGEMENT =====");
            System.out.println("1. Add Course");
            System.out.println("2. View Courses");
            System.out.println("3. Search Course");
            System.out.println("4. Delete Course");
            System.out.println("5. Back");

            System.out.print("Choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> {

                        System.out.print("Course Code: ");
                        String code = sc.nextLine();

                        System.out.print("Course Name: ");
                        String name = sc.nextLine();

                        System.out.print("Department: ");
                        String department = sc.nextLine();

                        System.out.print("Duration in years: ");
                        int duration = Integer.parseInt(
                                sc.nextLine()
                        );

                        if (controller.addCourse(
                                code, name, department, duration
                        )) {

                            System.out.println(
                                    "Course added successfully!"
                            );
                        }
                    }

                    case 2 -> {

                        List<Course> courses =
                                controller.getAllCourses();

                        if (courses.isEmpty()) {

                            System.out.println(
                                    "No courses found."
                            );

                        } else {

                            courses.forEach(System.out::println);
                        }
                    }

                    case 3 -> {

                        System.out.print("Course Code: ");
                        String code = sc.nextLine();

                        Course course =
                                controller.searchCourse(code);

                        if (course != null) {

                            System.out.println(course);

                        } else {

                            System.out.println(
                                    "Course not found."
                            );
                        }
                    }

                    case 4 -> {

                        System.out.print(
                                "Course Code to delete: "
                        );

                        String code = sc.nextLine();

                        if (controller.deleteCourse(code)) {

                            System.out.println(
                                    "Course deleted."
                            );

                        } else {

                            System.out.println(
                                    "Course not found."
                            );
                        }
                    }

                    case 5 -> {
                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (Exception e) {

                System.out.println(
                        "Operation failed: " + e.getMessage()
                );

                AppLogger.error(
                        "Course management failed", e
                );
            }
        }
    }


    // ================= SUBJECT MANAGEMENT =================

    private static void subjectManagementMenu(Scanner sc) {

        SubjectController controller =
                new SubjectController();

        while (true) {

            System.out.println("\n===== SUBJECT MANAGEMENT =====");
            System.out.println("1. Add Subject");
            System.out.println("2. View Subjects");
            System.out.println("3. Search Subject");
            System.out.println("4. Delete Subject");
            System.out.println("5. Back");

            System.out.print("Choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> {

                        System.out.print("Course ID: ");
                        int courseId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print("Subject Code: ");
                        String code = sc.nextLine();

                        System.out.print("Subject Name: ");
                        String name = sc.nextLine();

                        System.out.print("Semester: ");
                        int semester = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print("Credits: ");
                        int credits = Integer.parseInt(
                                sc.nextLine()
                        );

                        if (controller.addSubject(
                                courseId,
                                code,
                                name,
                                semester,
                                credits
                        )) {

                            System.out.println(
                                    "Subject added successfully!"
                            );
                        }
                    }

                    case 2 -> {

                        List<Subject> subjects =
                                controller.getAllSubjects();

                        if (subjects.isEmpty()) {

                            System.out.println(
                                    "No subjects found."
                            );

                        } else {

                            subjects.forEach(System.out::println);
                        }
                    }

                    case 3 -> {

                        System.out.print("Subject Code: ");
                        String code = sc.nextLine();

                        Subject subject =
                                controller.searchSubject(code);

                        if (subject != null) {

                            System.out.println(subject);

                        } else {

                            System.out.println(
                                    "Subject not found."
                            );
                        }
                    }

                    case 4 -> {

                        System.out.print(
                                "Subject Code to delete: "
                        );

                        String code = sc.nextLine();

                        if (controller.deleteSubject(code)) {

                            System.out.println(
                                    "Subject deleted."
                            );

                        } else {

                            System.out.println(
                                    "Subject not found."
                            );
                        }
                    }

                    case 5 -> {
                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (Exception e) {

                System.out.println(
                        "Operation failed: " + e.getMessage()
                );

                AppLogger.error(
                        "Subject management failed", e
                );
            }
        }
    }


    // ================= ACADEMIC OPERATIONS =================

    private static void academicOperationsMenu(
            Scanner sc, User user) {

        AcademicController controller =
                new AcademicController();

        while (true) {

            System.out.println("\n===== ACADEMIC OPERATIONS =====");
            System.out.println("1. Assign Faculty to Subject");
            System.out.println("2. Register Student for Subject");
            System.out.println("3. Add Timetable Entry");
            System.out.println("4. View Timetable");
            System.out.println("5. Post Notice");
            System.out.println("6. View Notices");
            System.out.println("7. Back");

            System.out.print("Choice: ");

            try {

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> {

                        System.out.print("Faculty ID: ");
                        int facultyId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print("Subject ID: ");
                        int subjectId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print(
                                "Academic Year (e.g. 2026-27): "
                        );

                        String year = sc.nextLine();

                        if (controller.assignFaculty(
                                facultyId, subjectId, year
                        )) {

                            System.out.println(
                                    "Faculty assigned successfully!"
                            );
                        }
                    }

                    case 2 -> {

                        System.out.print("Student ID: ");
                        int studentId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print("Subject ID: ");
                        int subjectId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print("Academic Year: ");
                        String year = sc.nextLine();

                        if (controller.registerStudentSubject(
                                studentId, subjectId, year
                        )) {

                            System.out.println(
                                    "Student registered successfully!"
                            );
                        }
                    }

                    case 3 -> {

                        System.out.print("Subject ID: ");
                        int subjectId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print("Faculty ID: ");
                        int facultyId = Integer.parseInt(
                                sc.nextLine()
                        );

                        System.out.print(
                                "Day (MONDAY, TUESDAY...): "
                        );

                        String day = sc.nextLine();

                        System.out.print(
                                "Start Time (HH:MM:SS): "
                        );

                        String start = sc.nextLine();

                        System.out.print(
                                "End Time (HH:MM:SS): "
                        );

                        String end = sc.nextLine();

                        System.out.print("Room Number: ");
                        String room = sc.nextLine();

                        if (controller.addTimetableEntry(
                                subjectId,
                                facultyId,
                                day,
                                start,
                                end,
                                room
                        )) {

                            System.out.println(
                                    "Timetable entry added!"
                            );
                        }
                    }

                    case 4 -> controller.viewTimetable();

                    case 5 -> {

                        System.out.print("Notice Title: ");
                        String title = sc.nextLine();

                        System.out.print("Notice Content: ");
                        String content = sc.nextLine();

                        if (controller.postNotice(
                                title,
                                content,
                                user.getUserId()
                        )) {

                            System.out.println(
                                    "Notice posted successfully!"
                            );
                        }
                    }

                    case 6 -> displayNotices();

                    case 7 -> {
                        return;
                    }

                    default -> System.out.println(
                            "Invalid choice."
                    );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (Exception e) {

                System.out.println(
                        "Operation failed: " + e.getMessage()
                );

                AppLogger.error(
                        "Academic operation failed", e
                );
            }
        }
    }


    // ================= VIEW NOTICES =================

    private static void displayNotices() {

        try {

            NoticeDAO dao = new NoticeDAO();
            List<Notice> notices = dao.getAllNotices();

            System.out.println(
                    "\n========== COLLEGE NOTICES =========="
            );

            if (notices.isEmpty()) {

                System.out.println(
                        "No notices available."
                );

                return;
            }

            for (Notice notice : notices) {

                System.out.println(notice);
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve notices."
            );

            AppLogger.error(
                    "Notice viewing failed", e
            );
        }
    }
}
