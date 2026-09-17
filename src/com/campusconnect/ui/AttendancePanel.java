package com.campusconnect.ui;

import com.campusconnect.model.Attendance;
import com.campusconnect.service.AttendanceService;

import javax.swing.*;
import java.awt.*;

public class AttendancePanel extends JPanel {

    private final AttendanceService attendanceService;

    private JTextField studentIdField;
    private JTextField dateField;
    private JComboBox<String> statusBox;

    public AttendancePanel(AttendanceService attendanceService) {

        this.attendanceService = attendanceService;

        setLayout(new BorderLayout());

        createTitle();
        createForm();
        createButtons();
    }

    private void createTitle() {

        JLabel title = new JLabel(
                "ATTENDANCE MANAGEMENT",
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

    private void createForm() {

        JPanel formPanel = new JPanel(
                new GridLayout(3, 2, 10, 15)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 60, 30, 60
                )
        );

        studentIdField = new JTextField();

        dateField = new JTextField();

        statusBox = new JComboBox<>(
                new String[]{"Present", "Absent"}
        );

        formPanel.add(
                new JLabel("Student ID:")
        );

        formPanel.add(studentIdField);

        formPanel.add(
                new JLabel("Date (DD-MM-YYYY):")
        );

        formPanel.add(dateField);

        formPanel.add(
                new JLabel("Status:")
        );

        formPanel.add(statusBox);

        add(
                formPanel,
                BorderLayout.CENTER
        );
    }

    private void createButtons() {

        JPanel buttonPanel = new JPanel();

        JButton markButton =
                new JButton("MARK ATTENDANCE");

        JButton viewButton =
                new JButton("VIEW ATTENDANCE");

        JButton percentageButton =
                new JButton("ATTENDANCE %");

        buttonPanel.add(markButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(percentageButton);

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        markButton.addActionListener(
                e -> markAttendance()
        );

        viewButton.addActionListener(
                e -> viewAttendance()
        );

        percentageButton.addActionListener(
                e -> showPercentage()
        );
    }

    private void markAttendance() {

        String studentId =
                studentIdField.getText().trim();

        String date =
                dateField.getText().trim();

        if (studentId.isEmpty() ||
                date.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student ID and date."
            );

            return;
        }

        boolean present =
                statusBox.getSelectedItem()
                        .equals("Present");

        Attendance attendance =
                new Attendance(
                        studentId,
                        date,
                        present
                );

        boolean added =
                attendanceService.markAttendance(
                        attendance
                );

        if (added) {

            JOptionPane.showMessageDialog(
                    this,
                    "Attendance marked successfully."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Attendance already exists for this student and date."
            );
        }
    }

    private void viewAttendance() {

        String studentId =
                studentIdField.getText().trim();

        if (studentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Student ID."
            );

            return;
        }

        java.util.List<Attendance> records =
                attendanceService
                        .getAttendanceForStudent(
                                studentId
                        );

        if (records.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No attendance records found."
            );

            return;
        }

        StringBuilder result =
                new StringBuilder();

        result.append(
                "Attendance Records\n\n"
        );

        for (Attendance attendance : records) {

            result.append(
                    "Date: "
            );

            result.append(
                    attendance.getDate()
            );

            result.append(
                    "   Status: "
            );

            result.append(
                    attendance.isPresent()
                            ? "Present"
                            : "Absent"
            );

            result.append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                result.toString(),
                "Attendance Records",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showPercentage() {

        String studentId =
                studentIdField.getText().trim();

        if (studentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Student ID."
            );

            return;
        }

        double percentage =
                attendanceService
                        .calculateAttendancePercentage(
                                studentId
                        );

        JOptionPane.showMessageDialog(
                this,
                String.format(
                        "Attendance: %.2f%%",
                        percentage
                ),
                "Attendance Percentage",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}