package com.campusconnect.ui;

import com.campusconnect.repository.AttendanceRepository;
import com.campusconnect.repository.FileRepository;
import com.campusconnect.repository.MarksRepository;

import com.campusconnect.service.AttendanceService;
import com.campusconnect.service.MarksService;
import com.campusconnect.service.StudentService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    private StudentService studentService;
    private AttendanceService attendanceService;
    private MarksService marksService;

    public MainFrame() {

        setTitle(
                "CampusConnect - Smart College Management System"
        );

        setSize(1100, 700);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        initializeServices();

        createHeader();
        createSidebar();
        createContent();
    }

    private void initializeServices() {

        FileRepository studentRepository =
                new FileRepository(
                        "data/students.csv"
                );

        AttendanceRepository attendanceRepository =
                new AttendanceRepository(
                        "data/attendance.csv"
                );

        MarksRepository marksRepository =
                new MarksRepository(
                        "data/marks.csv"
                );

        studentService =
                new StudentService(
                        studentRepository
                );

        attendanceService =
                new AttendanceService(
                        attendanceRepository
                );

        marksService =
                new MarksService(
                        marksRepository
                );
    }

    // =====================================================
    // HEADER
    // =====================================================

    private void createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBorder(
                new EmptyBorder(
                        18, 25, 18, 25
                )
        );

        JLabel logo =
                new JLabel("CAMPUSCONNECT");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        JLabel subtitle =
                new JLabel(
                        "Smart College Management System"
                );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        JPanel text =
                new JPanel();

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        text.setOpaque(false);

        text.add(logo);
        text.add(subtitle);

        header.add(
                text,
                BorderLayout.WEST
        );

        add(
                header,
                BorderLayout.NORTH
        );
    }

    // =====================================================
    // SIDEBAR
    // =====================================================

    private void createSidebar() {

        JPanel sidebar =
                new JPanel();

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setBorder(
                new EmptyBorder(
                        25, 15, 25, 15
                )
        );

        sidebar.setPreferredSize(
                new Dimension(190, 0)
        );

        JLabel menuTitle =
                new JLabel("MAIN MENU");

        menuTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        menuTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        sidebar.add(menuTitle);

        sidebar.add(
                Box.createVerticalStrut(20)
        );

        JButton dashboardButton =
                createMenuButton("Dashboard");

        JButton studentsButton =
                createMenuButton("Students");

        JButton attendanceButton =
                createMenuButton("Attendance");

        JButton marksButton =
                createMenuButton("Marks & Grades");

        JButton reportsButton =
                createMenuButton("Reports");

        sidebar.add(dashboardButton);

        sidebar.add(
                Box.createVerticalStrut(10)
        );

        sidebar.add(studentsButton);

        sidebar.add(
                Box.createVerticalStrut(10)
        );

        sidebar.add(attendanceButton);

        sidebar.add(
                Box.createVerticalStrut(10)
        );

        sidebar.add(marksButton);

        sidebar.add(
                Box.createVerticalStrut(10)
        );

        sidebar.add(reportsButton);

        add(
                sidebar,
                BorderLayout.WEST
        );

        dashboardButton.addActionListener(
                e -> showDashboard()
        );

        studentsButton.addActionListener(
                e -> showPanel(
                        new StudentManagementPanel(
                                studentService
                        )
                )
        );

        attendanceButton.addActionListener(
                e -> showPanel(
                        new AttendancePanel(
                                attendanceService
                        )
                )
        );

        marksButton.addActionListener(
                e -> showPanel(
                        new MarksPanel(
                                marksService
                        )
                )
        );

        reportsButton.addActionListener(
                e -> showPanel(
                        new ReportsPanel(
                                studentService,
                                attendanceService,
                                marksService
                        )
                )
        );
    }

    private JButton createMenuButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setMaximumSize(
                new Dimension(165, 42)
        );

        button.setPreferredSize(
                new Dimension(165, 42)
        );

        button.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        return button;
    }

    // =====================================================
    // CONTENT
    // =====================================================

    private void createContent() {

        contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        showDashboard();
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private void showDashboard() {

        JPanel dashboard =
                new JPanel(new BorderLayout());

        dashboard.setBorder(
                new EmptyBorder(
                        30, 35, 30, 35
                )
        );

        // -----------------------------
        // TOP WELCOME SECTION
        // -----------------------------

        JPanel welcomePanel =
                new JPanel();

        welcomePanel.setLayout(
                new BoxLayout(
                        welcomePanel,
                        BoxLayout.Y_AXIS
                )
        );

        welcomePanel.setBorder(
                new EmptyBorder(
                        0, 0, 25, 0
                )
        );

        JLabel welcome =
                new JLabel(
                        "Welcome back 👋"
                );

        welcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        JLabel description =
                new JLabel(
                        "Here's what's happening in CampusConnect today."
                );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        welcomePanel.add(welcome);

        welcomePanel.add(
                Box.createVerticalStrut(5)
        );

        welcomePanel.add(description);

        dashboard.add(
                welcomePanel,
                BorderLayout.NORTH
        );

        // -----------------------------
        // MAIN AREA
        // -----------------------------

        JPanel mainArea =
                new JPanel();

        mainArea.setLayout(
                new BoxLayout(
                        mainArea,
                        BoxLayout.Y_AXIS
                )
        );

        // -----------------------------
        // STATISTICS
        // -----------------------------

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                18,
                                0
                        )
                );

        int totalStudents =
                studentService
                        .getAllStudents()
                        .size();

        int attendanceStudents =
                countStudentsWithAttendance();

        int marksStudents =
                countStudentsWithMarks();

        statsPanel.add(
                createDashboardCard(
                        "TOTAL STUDENTS",
                        String.valueOf(
                                totalStudents
                        )
                )
        );

        statsPanel.add(
                createDashboardCard(
                        "ATTENDANCE",
                        String.valueOf(
                                attendanceStudents
                        )
                )
        );

        statsPanel.add(
                createDashboardCard(
                        "MARKS RECORDS",
                        String.valueOf(
                                marksStudents
                        )
                )
        );

        mainArea.add(statsPanel);

        mainArea.add(
                Box.createVerticalStrut(30)
        );

        // -----------------------------
        // QUICK ACCESS TITLE
        // -----------------------------

        JLabel quickTitle =
                new JLabel(
                        "Quick Access"
                );

        quickTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20
                )
        );

        mainArea.add(quickTitle);

        mainArea.add(
                Box.createVerticalStrut(15)
        );

        // -----------------------------
        // QUICK ACCESS CARDS
        // -----------------------------

        JPanel row1 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                15
                        )
                );

        JPanel row2 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                15
                        )
                );

        row1.add(
                createQuickCard(
                        "Student Management",
                        "Add, update, delete and view students.",
                        () -> showPanel(
                                new StudentManagementPanel(
                                        studentService
                                )
                        )
                )
        );

        row1.add(
                createQuickCard(
                        "Attendance",
                        "Manage student attendance records.",
                        () -> showPanel(
                                new AttendancePanel(
                                        attendanceService
                                )
                        )
                )
        );

        row2.add(
                createQuickCard(
                        "Marks & Grades",
                        "Manage marks and calculate grades.",
                        () -> showPanel(
                                new MarksPanel(
                                        marksService
                                )
                        )
                )
        );

        row2.add(
                createQuickCard(
                        "Student Reports",
                        "View complete student performance reports.",
                        () -> showPanel(
                                new ReportsPanel(
                                        studentService,
                                        attendanceService,
                                        marksService
                                )
                        )
                )
        );

        mainArea.add(row1);

        mainArea.add(
                Box.createVerticalStrut(15)
        );

        mainArea.add(row2);

        dashboard.add(
                mainArea,
                BorderLayout.CENTER
        );

        // -----------------------------
        // FOOTER
        // -----------------------------

        JLabel footer =
                new JLabel(
                        "CampusConnect • Student Management Made Simple",
                        SwingConstants.CENTER
                );

        footer.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        dashboard.add(
                footer,
                BorderLayout.SOUTH
        );

        showPanel(dashboard);
    }

    // =====================================================
    // STAT CARD
    // =====================================================

    private JPanel createDashboardCard(
            String title,
            String value
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setPreferredSize(
                new Dimension(
                        200,
                        120
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY,
                                1
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        JLabel valueLabel =
                new JLabel(
                        value,
                        SwingConstants.CENTER
                );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =====================================================
    // QUICK ACCESS CARD
    // =====================================================

    private JPanel createQuickCard(
            String title,
            String description,
            Runnable action
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                5
                        )
                );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY,
                                1
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        JLabel descriptionLabel =
                new JLabel(
                        "<html>" +
                                description +
                                "</html>"
                );

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        JButton openButton =
                new JButton("OPEN");

        openButton.setFocusPainted(false);

        openButton.addActionListener(
                e -> action.run()
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                descriptionLabel,
                BorderLayout.CENTER
        );

        card.add(
                openButton,
                BorderLayout.EAST
        );

        return card;
    }

    // =====================================================
    // DASHBOARD DATA
    // =====================================================

    private int countStudentsWithAttendance() {

        int count = 0;

        for (var student :
                studentService.getAllStudents()) {

            if (!attendanceService
                    .getAttendanceForStudent(
                            student.getStudentId()
                    )
                    .isEmpty()) {

                count++;
            }
        }

        return count;
    }

    private int countStudentsWithMarks() {

        int count = 0;

        for (var student :
                studentService.getAllStudents()) {

            if (!marksService
                    .getMarksForStudent(
                            student.getStudentId()
                    )
                    .isEmpty()) {

                count++;
            }
        }

        return count;
    }

    // =====================================================
    // PANEL SWITCHING
    // =====================================================

    private void showPanel(
            JPanel panel
    ) {

        contentPanel.removeAll();

        contentPanel.add(
                panel,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();

        contentPanel.repaint();
    }
}