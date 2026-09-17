package com.campusconnect.ui;

import com.campusconnect.model.Marks;
import com.campusconnect.model.Student;
import com.campusconnect.service.AttendanceService;
import com.campusconnect.service.MarksService;
import com.campusconnect.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReportsPanel extends JPanel {

    private final StudentService studentService;
    private final AttendanceService attendanceService;
    private final MarksService marksService;

    private JTextField studentIdField;
    private JTextArea reportArea;

    public ReportsPanel(
            StudentService studentService,
            AttendanceService attendanceService,
            MarksService marksService
    ) {

        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.marksService = marksService;

        setLayout(new BorderLayout());

        createTitle();
        createTopPanel();
        createReportArea();
    }

    private void createTitle() {

        JLabel title = new JLabel(
                "STUDENT REPORTS",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 10, 20, 10
                )
        );

        add(title, BorderLayout.NORTH);
    }

    private void createTopPanel() {

        JPanel panel = new JPanel();

        panel.add(
                new JLabel("Student ID:")
        );

        studentIdField =
                new JTextField(15);

        panel.add(studentIdField);

        JButton generateButton =
                new JButton("GENERATE REPORT");

        panel.add(generateButton);

        generateButton.addActionListener(
                e -> generateReport()
        );

        add(
                panel,
                BorderLayout.CENTER
        );
    }

    private void createReportArea() {

        reportArea =
                new JTextArea();

        reportArea.setEditable(false);

        reportArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(reportArea);

        scrollPane.setPreferredSize(
                new Dimension(700, 300)
        );

        add(
                scrollPane,
                BorderLayout.SOUTH
        );
    }

    private void generateReport() {

        String studentId =
                studentIdField.getText().trim();

        if (studentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Student ID."
            );

            return;
        }

        Student student =
                studentService.findStudentById(
                        studentId
                );

        if (student == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );

            return;
        }

        double attendance =
                attendanceService
                        .calculateAttendancePercentage(
                                studentId
                        );

        List<Marks> marksList =
                marksService.getMarksForStudent(
                        studentId
                );

        double average =
                marksService.calculateAverage(
                        studentId
                );

        String grade =
                marksService.calculateGrade(
                        studentId
                );

        StringBuilder report =
                new StringBuilder();

        report.append(
                "========================================\n"
        );

        report.append(
                "             STUDENT REPORT\n"
        );

        report.append(
                "========================================\n\n"
        );

        report.append(
                "Student ID : "
        );

        report.append(
                student.getStudentId()
        );

        report.append("\n");

        report.append(
                "Name       : "
        );

        report.append(
                student.getName()
        );

        report.append("\n");

        report.append(
                "Branch     : "
        );

        report.append(
                student.getBranch()
        );

        report.append("\n");

        report.append(
                "Semester   : "
        );

        report.append(
                student.getSemester()
        );

        report.append("\n\n");

        report.append(
                "------------- ATTENDANCE -------------\n"
        );

        report.append(
                String.format(
                        "Attendance : %.2f%%\n\n",
                        attendance
                )
        );

        report.append(
                "--------------- MARKS -----------------\n"
        );

        if (marksList.isEmpty()) {

            report.append(
                    "No marks records found.\n"
            );

        } else {

            for (Marks mark : marksList) {

                report.append(
                        String.format(
                                "%-20s %.2f\n",
                                mark.getSubject(),
                                mark.getMarks()
                        )
                );
            }

            report.append("\n");

            report.append(
                    String.format(
                            "Average    : %.2f\n",
                            average
                    )
            );

            report.append(
                    "Grade      : "
            );

            report.append(grade);

            report.append("\n");
        }

        report.append(
                "\n========================================\n"
        );

        reportArea.setText(
                report.toString()
        );
    }
}