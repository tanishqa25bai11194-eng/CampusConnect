package com.campusconnect.ui;

import com.campusconnect.model.Student;
import com.campusconnect.service.StudentService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentManagementPanel extends JPanel {

    private final StudentService studentService;

    private JTextField idField;
    private JTextField nameField;
    private JTextField branchField;
    private JTextField semesterField;

    private JTextArea studentListArea;

    public StudentManagementPanel(StudentService studentService) {

        this.studentService = studentService;

        setLayout(new BorderLayout(10, 10));

        createTitle();
        createForm();
        createButtons();
        createStudentList();
    }

    private void createTitle() {

        JLabel title = new JLabel(
                "STUDENT MANAGEMENT",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 10, 10
                )
        );

        add(title, BorderLayout.NORTH);
    }

    private void createForm() {

        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 40, 10, 40
                )
        );

        idField = new JTextField();
        nameField = new JTextField();
        branchField = new JTextField();
        semesterField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Branch:"));
        formPanel.add(branchField);

        formPanel.add(new JLabel("Semester:"));
        formPanel.add(semesterField);

        add(formPanel, BorderLayout.CENTER);
    }

    private void createButtons() {

        JPanel buttonPanel = new JPanel();

        JButton addButton =
                new JButton("ADD");

        JButton searchButton =
                new JButton("SEARCH");

        JButton updateButton =
                new JButton("UPDATE");

        JButton deleteButton =
                new JButton("DELETE");

        JButton viewButton =
                new JButton("VIEW ALL");

        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        addButton.addActionListener(
                e -> addStudent()
        );

        searchButton.addActionListener(
                e -> searchStudent()
        );

        updateButton.addActionListener(
                e -> updateStudent()
        );

        deleteButton.addActionListener(
                e -> deleteStudent()
        );

        viewButton.addActionListener(
                e -> viewAllStudents()
        );
    }

    private void createStudentList() {

        studentListArea =
                new JTextArea();

        studentListArea.setEditable(false);

        studentListArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(studentListArea);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Student Records"
                )
        );

        add(
                scrollPane,
                BorderLayout.EAST
        );
    }

    private void addStudent() {

        try {

            String id =
                    idField.getText().trim();

            String name =
                    nameField.getText().trim();

            String branch =
                    branchField.getText().trim();

            if (id.isEmpty()
                    || name.isEmpty()
                    || branch.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields."
                );

                return;
            }

            int semester =
                    Integer.parseInt(
                            semesterField
                                    .getText()
                                    .trim()
                    );

            if (semester < 1 || semester > 8) {

                JOptionPane.showMessageDialog(
                        this,
                        "Semester must be between 1 and 8."
                );

                return;
            }

            Student student =
                    new Student(
                            id,
                            name,
                            branch,
                            semester
                    );

            if (studentService.addStudent(student)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student added successfully."
                );

                clearFields();
                viewAllStudents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student ID already exists."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semester must be a number."
            );
        }
    }

    private void searchStudent() {

        String id =
                idField.getText().trim();

        Student student =
                studentService.findStudentById(id);

        if (student == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );

            return;
        }

        nameField.setText(
                student.getName()
        );

        branchField.setText(
                student.getBranch()
        );

        semesterField.setText(
                String.valueOf(
                        student.getSemester()
                )
        );
    }

    private void updateStudent() {

        try {

            String id =
                    idField.getText().trim();

            String name =
                    nameField.getText().trim();

            String branch =
                    branchField.getText().trim();

            int semester =
                    Integer.parseInt(
                            semesterField
                                    .getText()
                                    .trim()
                    );

            boolean updated =
                    studentService.updateStudent(
                            id,
                            name,
                            branch,
                            semester
                    );

            if (updated) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student updated successfully."
                );

                viewAllStudents();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Student not found."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semester must be a number."
            );
        }
    }

    private void deleteStudent() {

        String id =
                idField.getText().trim();

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete student " + id + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (studentService.deleteStudent(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student deleted successfully."
            );

            clearFields();
            viewAllStudents();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found."
            );
        }
    }

    private void viewAllStudents() {

        List<Student> students =
                studentService.getAllStudents();

        studentListArea.setText("");

        if (students.isEmpty()) {

            studentListArea.setText(
                    "No students found."
            );

            return;
        }

        studentListArea.append(
                "ID       NAME              BRANCH      SEM\n"
        );

        studentListArea.append(
                "------------------------------------------------\n"
        );

        for (Student student : students) {

            studentListArea.append(
                    String.format(
                            "%-8s %-17s %-10s %d%n",
                            student.getStudentId(),
                            student.getName(),
                            student.getBranch(),
                            student.getSemester()
                    )
            );
        }
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        branchField.setText("");
        semesterField.setText("");
    }
}