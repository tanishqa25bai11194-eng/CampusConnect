package com.campusconnect;

import com.campusconnect.model.Attendance;
import com.campusconnect.model.Marks;
import com.campusconnect.model.Student;
import com.campusconnect.repository.AttendanceRepository;
import com.campusconnect.repository.FileRepository;
import com.campusconnect.repository.MarksRepository;
import com.campusconnect.service.AttendanceService;
import com.campusconnect.service.MarksService;
import com.campusconnect.service.ReportService;
import com.campusconnect.service.StudentService;
import com.campusconnect.util.InputValidator;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService =
            new StudentService(
                    new FileRepository("data/students.csv")
            );

    private static final AttendanceService attendanceService =
            new AttendanceService(
                    new AttendanceRepository("data/attendance.csv")
            );

    private static final MarksService marksService =
            new MarksService(
                    new MarksRepository("data/marks.csv")
            );

    private static final ReportService reportService =
            new ReportService(
                    studentService,
                    attendanceService,
                    marksService
            );

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("          CAMPUSCONNECT");
        System.out.println("   Smart College Management System");
        System.out.println("======================================");

        while (true) {

            showMainMenu();

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    studentManagement();
                    break;

                case 2:
                    viewAllStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    attendanceManagement();
                    break;

                case 5:
                    marksManagement();
                    break;

                case 6:
                    reportManagement();
                    break;

                case 7:
                    System.out.println(
                            "Thank you for using CampusConnect!"
                    );
                    scanner.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please enter 1 to 7."
                    );
            }
        }
    }

    // =====================================
    // MAIN MENU
    // =====================================

    private static void showMainMenu() {

        System.out.println("\n======================================");
        System.out.println("              MAIN MENU");
        System.out.println("======================================");
        System.out.println("1. Student Management");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Attendance Management");
        System.out.println("5. Marks & Grade Management");
        System.out.println("6. Reports & Analytics");
        System.out.println("7. Exit");
        System.out.println("======================================");
    }

    // =====================================
    // STUDENT MANAGEMENT
    // =====================================

    private static void studentManagement() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("         STUDENT MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View All Students");
            System.out.println("5. Back to Main Menu");
            System.out.println("======================================");

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    updateStudent();
                    break;

                case 3:
                    deleteStudent();
                    break;

                case 4:
                    viewAllStudents();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void addStudent() {

        System.out.println("\n---------- ADD STUDENT ----------");

        String studentId =
                readString("Enter Student ID: ");

        if (!InputValidator.isValidStudentId(studentId)) {
            System.out.println("Student ID cannot be empty.");
            return;
        }

        if (studentService.findStudentById(studentId) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        String name =
                readString("Enter Student Name: ");

        if (!InputValidator.isValidName(name)) {
            System.out.println("Name cannot be empty.");
            return;
        }

        String branch =
                readString("Enter Branch: ");

        if (!InputValidator.isValidBranch(branch)) {
            System.out.println("Branch cannot be empty.");
            return;
        }

        int semester =
                readInteger("Enter Semester (1-8): ");

        if (!InputValidator.isValidSemester(semester)) {
            System.out.println(
                    "Semester must be between 1 and 8."
            );
            return;
        }

        Student student =
                new Student(
                        studentId,
                        name,
                        branch,
                        semester
                );

        boolean added =
                studentService.addStudent(student);

        if (added) {
            System.out.println(
                    "Student added successfully."
            );
        } else {
            System.out.println(
                    "Student could not be added."
            );
        }
    }

    private static void updateStudent() {

        System.out.println("\n---------- UPDATE STUDENT ----------");

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println(
                "Current Name: " + student.getName()
        );

        System.out.println(
                "Current Branch: " + student.getBranch()
        );

        System.out.println(
                "Current Semester: " + student.getSemester()
        );

        String name =
                readString("Enter New Name: ");

        if (!InputValidator.isValidName(name)) {
            System.out.println("Name cannot be empty.");
            return;
        }

        String branch =
                readString("Enter New Branch: ");

        if (!InputValidator.isValidBranch(branch)) {
            System.out.println("Branch cannot be empty.");
            return;
        }

        int semester =
                readInteger("Enter New Semester (1-8): ");

        if (!InputValidator.isValidSemester(semester)) {
            System.out.println(
                    "Semester must be between 1 and 8."
            );
            return;
        }

        boolean updated =
                studentService.updateStudent(
                        studentId,
                        name,
                        branch,
                        semester
                );

        if (updated) {
            System.out.println(
                    "Student updated successfully."
            );
        } else {
            System.out.println(
                    "Student could not be updated."
            );
        }
    }

    private static void deleteStudent() {

        System.out.println("\n---------- DELETE STUDENT ----------");

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println(
                "Student Name: " + student.getName()
        );

        String confirmation =
                readString(
                        "Are you sure? Enter yes or no: "
                );

        if (confirmation.equalsIgnoreCase("yes")) {

            boolean deleted =
                    studentService.deleteStudent(studentId);

            if (deleted) {
                System.out.println(
                        "Student deleted successfully."
                );
            } else {
                System.out.println(
                        "Student could not be deleted."
                );
            }

        } else {

            System.out.println(
                    "Delete operation cancelled."
            );
        }
    }

    private static void viewAllStudents() {

        System.out.println("\n---------- ALL STUDENTS ----------");

        List<Student> students =
                studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println(
                "Total Students: " + students.size()
        );
    }

    // =====================================
    // SEARCH STUDENT
    // =====================================

    private static void searchStudent() {

        System.out.println("\n---------- SEARCH STUDENT ----------");

        String name =
                readString("Enter student name: ");

        List<Student> results =
                studentService.searchStudentsByName(name);

        if (results.isEmpty()) {

            System.out.println("No students found.");

        } else {

            System.out.println("\nSearch Results:");

            for (Student student : results) {
                System.out.println(student);
            }
        }
    }

    // =====================================
    // ATTENDANCE MANAGEMENT
    // =====================================

    private static void attendanceManagement() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("        ATTENDANCE MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Calculate Attendance Percentage");
            System.out.println("4. Back to Main Menu");
            System.out.println("======================================");

            int choice =
                    readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    markAttendance();
                    break;

                case 2:
                    viewAttendance();
                    break;

                case 3:
                    calculateAttendance();
                    break;

                case 4:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void markAttendance() {

        System.out.println("\n---------- MARK ATTENDANCE ----------");

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String date =
                readString("Enter Date (DD-MM-YYYY): ");

        if (date.isEmpty()) {
            System.out.println("Date cannot be empty.");
            return;
        }

        String status =
                readString("Enter Status (P/A): ");

        if (!status.equalsIgnoreCase("P")
                && !status.equalsIgnoreCase("A")) {

            System.out.println(
                    "Invalid status. Use P for Present or A for Absent."
            );

            return;
        }

        boolean present =
                status.equalsIgnoreCase("P");

        Attendance attendance =
                new Attendance(
                        studentId,
                        date,
                        present
                );

        boolean marked =
                attendanceService.markAttendance(attendance);

        if (marked) {

            System.out.println(
                    "Attendance marked successfully."
            );

        } else {

            System.out.println(
                    "Attendance already exists for this student on this date."
            );
        }
    }

    private static void viewAttendance() {

        System.out.println("\n---------- VIEW ATTENDANCE ----------");

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        List<Attendance> attendanceList =
                attendanceService.getAttendanceForStudent(studentId);

        if (attendanceList.isEmpty()) {

            System.out.println(
                    "No attendance records found."
            );

            return;
        }

        System.out.println(
                "\nAttendance for " + student.getName()
        );

        for (Attendance attendance : attendanceList) {
            System.out.println(attendance);
        }
    }

    private static void calculateAttendance() {

        System.out.println(
                "\n---------- ATTENDANCE PERCENTAGE ----------"
        );

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double percentage =
                attendanceService
                        .calculateAttendancePercentage(studentId);

        System.out.println(
                "Student: " + student.getName()
        );

        System.out.printf(
                "Attendance Percentage: %.2f%%%n",
                percentage
        );
    }

    // =====================================
    // MARKS MANAGEMENT
    // =====================================

    private static void marksManagement() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("       MARKS & GRADE MANAGEMENT");
            System.out.println("======================================");
            System.out.println("1. Add Marks");
            System.out.println("2. View Student Marks");
            System.out.println("3. Calculate Average");
            System.out.println("4. Calculate Grade");
            System.out.println("5. Back to Main Menu");
            System.out.println("======================================");

            int choice =
                    readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addMarks();
                    break;

                case 2:
                    viewMarks();
                    break;

                case 3:
                    calculateAverage();
                    break;

                case 4:
                    calculateGrade();
                    break;

                case 5:
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    private static void addMarks() {

        System.out.println("\n---------- ADD MARKS ----------");

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String subject =
                readString("Enter Subject: ");

        if (subject.isEmpty()) {
            System.out.println("Subject cannot be empty.");
            return;
        }

        double marks =
                readDouble("Enter Marks (0-100): ");

        if (marks < 0 || marks > 100) {

            System.out.println(
                    "Marks must be between 0 and 100."
            );

            return;
        }

        Marks marksRecord =
                new Marks(
                        studentId,
                        subject,
                        marks
                );

        marksService.addMarks(marksRecord);

        System.out.println(
                "Marks added successfully."
        );
    }

    private static void viewMarks() {

        System.out.println("\n---------- VIEW MARKS ----------");

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        List<Marks> marksList =
                marksService.getMarksForStudent(studentId);

        if (marksList.isEmpty()) {

            System.out.println(
                    "No marks records found."
            );

            return;
        }

        System.out.println(
                "\nMarks for " + student.getName()
        );

        for (Marks marks : marksList) {
            System.out.println(marks);
        }
    }

    private static void calculateAverage() {

        System.out.println(
                "\n---------- CALCULATE AVERAGE ----------"
        );

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double average =
                marksService.calculateAverage(studentId);

        System.out.println(
                "Student: " + student.getName()
        );

        System.out.printf(
                "Average Marks: %.2f%n",
                average
        );
    }

    private static void calculateGrade() {

        System.out.println(
                "\n---------- CALCULATE GRADE ----------"
        );

        String studentId =
                readString("Enter Student ID: ");

        Student student =
                studentService.findStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double average =
                marksService.calculateAverage(studentId);

        String grade =
                marksService.calculateGrade(studentId);

        System.out.println(
                "Student: " + student.getName()
        );

        System.out.printf(
                "Average Marks: %.2f%n",
                average
        );

        System.out.println(
                "Grade: " + grade
        );
    }

    // =====================================
    // REPORT MANAGEMENT
    // =====================================

    private static void reportManagement() {

        System.out.println("\n---------- STUDENT REPORT ----------");

        String studentId =
                readString("Enter Student ID: ");

        reportService.generateStudentReport(studentId);
    }

    // =====================================
    // INPUT METHODS
    // =====================================

    private static String readString(String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    private static int readInteger(String message) {

        while (true) {

            try {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}