
package com.campusconnect.util;

public class InputValidator {

    private InputValidator() {
        // Utility class
    }

    public static boolean isValidStudentId(
            String studentId) {

        return studentId != null
                && !studentId.trim().isEmpty();
    }

    public static boolean isValidName(String name) {

        return name != null
                && !name.trim().isEmpty();
    }

    public static boolean isValidBranch(String branch) {

        return branch != null
                && !branch.trim().isEmpty();
    }

    public static boolean isValidSemester(int semester) {

        return semester >= 1 && semester <= 8;
    }
}
