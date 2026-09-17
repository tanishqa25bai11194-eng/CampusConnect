package com.campusconnect.service;

import com.campusconnect.model.Marks;
import com.campusconnect.repository.MarksRepository;

import java.util.ArrayList;
import java.util.List;

public class MarksService {

    private final MarksRepository repository;
    private final List<Marks> marksList;

    public MarksService(MarksRepository repository) {

        this.repository = repository;

        this.marksList = new ArrayList<>(
                repository.loadMarks()
        );
    }

    public void addMarks(Marks marks) {

        marksList.add(marks);

        repository.saveMarks(marksList);
    }

    public List<Marks> getMarksForStudent(String studentId) {

        List<Marks> result = new ArrayList<>();

        for (Marks marks : marksList) {

            if (marks.getStudentId()
                    .equalsIgnoreCase(studentId)) {

                result.add(marks);
            }
        }

        return result;
    }

    public double calculateAverage(String studentId) {

        List<Marks> studentMarks =
                getMarksForStudent(studentId);

        if (studentMarks.isEmpty()) {
            return 0.0;
        }

        double total = 0;

        for (Marks marks : studentMarks) {

            total += marks.getMarks();
        }

        return total / studentMarks.size();
    }

    public String calculateGrade(String studentId) {

        double average =
                calculateAverage(studentId);

        if (average >= 90) {

            return "A+";

        } else if (average >= 80) {

            return "A";

        } else if (average >= 70) {

            return "B";

        } else if (average >= 60) {

            return "C";

        } else if (average >= 50) {

            return "D";

        } else {

            return "F";
        }
    }
}