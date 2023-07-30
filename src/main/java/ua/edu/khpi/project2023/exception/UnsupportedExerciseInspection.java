package ua.edu.khpi.project2023.exception;

import org.springframework.http.HttpStatus;

public class UnsupportedExerciseInspection extends Exception {
    public UnsupportedExerciseInspection(String message) {
        super(message);
    }
}
