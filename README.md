# CampusConnect - Smart College Student Management System

## Overview

CampusConnect is a Java-based college student management system designed to simplify the management of student records, attendance, marks, grades, and student performance reports.

The project provides a simple graphical user interface (GUI) using Java Swing and stores data using CSV files.

CampusConnect is designed as a modular application with separate model, repository, service, utility, and user-interface components.

---

## Problem Statement

Managing student information, attendance, marks, and academic performance manually can be time-consuming and difficult to maintain.

CampusConnect provides a centralized system where student information and academic records can be managed through a simple graphical interface.

The system allows users to manage student records, record attendance, manage marks, calculate grades, and generate student performance reports.

---

## Objectives

The main objectives of CampusConnect are:

- Manage student information efficiently.
- Add, update, search, delete, and view student records.
- Record and view student attendance.
- Calculate attendance percentages.
- Store and manage student marks.
- Calculate average marks and grades.
- Generate student performance reports.
- Provide a simple and user-friendly graphical interface.
- Maintain data using file-based storage.

---

## Features

### 1. Student Management

Users can:

- Add new students.
- Search for students.
- Update student information.
- Delete student records.
- View all registered students.

Student information includes:

- Student ID
- Name
- Branch
- Semester

### 2. Attendance Management

Users can:

- Record student attendance.
- Mark students as Present or Absent.
- View attendance records.
- Calculate attendance percentage.
- Prevent duplicate attendance records for the same student and date.

### 3. Marks & Grades

Users can:

- Add marks for students.
- View student marks.
- Calculate average marks.
- Automatically calculate grades based on average marks.

### 4. Student Reports

The system can generate a student report containing:

- Student information
- Attendance percentage
- Subject-wise marks
- Average marks
- Grade

### 5. Dashboard

The dashboard provides:

- Total student count
- Attendance-related statistics
- Marks-related statistics
- Quick access to major system modules

---

## Technologies Used

- **Programming Language:** Java
- **GUI:** Java Swing
- **IDE:** IntelliJ IDEA
- **Data Storage:** CSV files
- **Version Control:** Git
- **Repository:** GitHub

No external libraries or frameworks are required.

---

## System Architecture

CampusConnect follows a modular layered structure:

```text
User
  |
  v
Java Swing GUI
  |
  v
Service Layer
  |
  v
Repository Layer
  |
  v
CSV File Storage
```
PROJECT STRUCTURE 
```
CampusConnect/
│
├── data/
│   └── students.csv
│
├── src/
│   └── com/
│       └── campusconnect/
│           │
│           ├── Main.java
│           │
│           ├── model/
│           │   ├── Student.java
│           │   ├── Attendance.java
│           │   └── Marks.java
│           │
│           ├── repository/
│           │   ├── FileRepository.java
│           │   ├── AttendanceRepository.java
│           │   └── MarksRepository.java
│           │
│           ├── service/
│           │   ├── StudentService.java
│           │   ├── AttendanceService.java
│           │   ├── MarksService.java
│           │   └── ReportService.java
│           │
│           ├── util/
│           │   └── InputValidator.java
│           │
│           └── ui/
│               ├── CampusConnectGUI.java
│               ├── MainFrame.java
│               ├── StudentManagementPanel.java
│               ├── AttendancePanel.java
│               ├── MarksPanel.java
│               └── ReportsPanel.java
│
├── README.md
└── statement.md
```
