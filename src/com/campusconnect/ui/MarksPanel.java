package com.campusconnect.ui;

import com.campusconnect.model.Marks;
import com.campusconnect.service.MarksService;

import javax.swing.*;
import java.awt.*;

public class MarksPanel extends JPanel {

    private final MarksService marksService;

    private JTextField studentIdField;
    private JTextField subjectField;
    private JTextField marksField;

    public MarksPanel(MarksService marksService) {

        this.marksService = marksService;

        setLayout(new BorderLayout());

        createTitle();
        createForm();
        createButtons();
    }

    private void createTitle() {

        JLabel title = new JLabel(
                "MARKS & GRADES",
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

        subjectField = new JTextField();

        marksField = new JTextField();

        formPanel.add(
                new JLabel("Student ID:")
        );

        formPanel.add(studentIdField);

        formPanel.add(
                new JLabel("Subject:")
        );

        formPanel.add(subjectField);

        formPanel.add(
                new JLabel("Marks:")
        );

        formPanel.add(marksField);

        add(
                formPanel,
                BorderLayout.CENTER
        );
    }

    private void createButtons() {

        JPanel buttonPanel = new JPanel();

        JButton addButton =
                new JButton("ADD MARKS");

        JButton viewButton =
                new JButton("VIEW MARKS");

        JButton averageButton =
                new JButton("AVERAGE");

        JButton gradeButton =
                new JButton("GRADE");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(averageButton);
        buttonPanel.add(gradeButton);

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        addButton.addActionListener(
                e -> addMarks()
        );

        viewButton.addActionListener(
                e -> viewMarks()
        );

        averageButton.addActionListener(
                e -> showAverage()
        );

        gradeButton.addActionListener(
                e -> showGrade()
        );
    }

    private void addMarks() {

        try {

            String studentId =
                    studentIdField.getText().trim();

            String subject =
                    subjectField.getText().trim();

            if (studentId.isEmpty() ||
                    subject.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields."
                );

                return;
            }

            double marks =
                    Double.parseDouble(
                            marksField.getText().trim()
                    );

            if (marks < 0 || marks > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be between 0 and 100."
                );

                return;
            }

            Marks mark =
                    new Marks(
                            studentId,
                            subject,
                            marks
                    );

            marksService.addMarks(mark);

            JOptionPane.showMessageDialog(
                    this,
                    "Marks added successfully."
            );

            marksField.setText("");

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Marks must be a number."
            );
        }
    }

    private void viewMarks() {

        String studentId =
                studentIdField.getText().trim();

        if (studentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Student ID."
            );

            return;
        }

        java.util.List<Marks> records =
                marksService.getMarksForStudent(
                        studentId
                );

        if (records.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No marks records found."
            );

            return;
        }

        StringBuilder result =
                new StringBuilder();

        result.append(
                "Marks Records\n\n"
        );

        for (Marks mark : records) {

            result.append(
                    mark.getSubject()
            );

            result.append(
                    " : "
            );

            result.append(
                    mark.getMarks()
            );

            result.append("\n");
        }

        JOptionPane.showMessageDialog(
                this,
                result.toString(),
                "Marks Records",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAverage() {

        String studentId =
                studentIdField.getText().trim();

        if (studentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Student ID."
            );

            return;
        }

        double average =
                marksService.calculateAverage(
                        studentId
                );

        JOptionPane.showMessageDialog(
                this,
                String.format(
                        "Average Marks: %.2f",
                        average
                ),
                "Average",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showGrade() {

        String studentId =
                studentIdField.getText().trim();

        if (studentId.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Student ID."
            );

            return;
        }

        String grade =
                marksService.calculateGrade(
                        studentId
                );

        JOptionPane.showMessageDialog(
                this,
                "Grade: " + grade,
                "Grade",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}