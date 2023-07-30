package ua.edu.khpi.project2023.exercise.model;

import ua.edu.khpi.project2023.exercise.ExerciseType;

public interface IExercise {
    ExerciseType getType();
    void setType(ExerciseType type);
}
