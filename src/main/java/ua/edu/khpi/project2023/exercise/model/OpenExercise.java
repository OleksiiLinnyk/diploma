package ua.edu.khpi.project2023.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.khpi.project2023.exercise.ExerciseType;

import java.util.List;

@Data
@AllArgsConstructor
public class OpenExercise implements Exercise {

    private ExerciseType type;

    private String question;
    private String givenAnswer;
    private int points;

    @Override
    public ExerciseType getType() {
        return type;
    }

    @Override
    public void setType(ExerciseType type) {
        this.type = type;
    }
}
