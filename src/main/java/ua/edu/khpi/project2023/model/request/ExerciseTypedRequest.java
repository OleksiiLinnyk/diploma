package ua.edu.khpi.project2023.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.khpi.project2023.exercise.ExerciseType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseTypedRequest {
    private ExerciseType type;
    private String question;
    private List<String> options = null;
    private int points;
}
