package ua.edu.khpi.project2023.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.khpi.project2023.exercise.ExerciseType;
import ua.edu.khpi.project2023.exercise.model.IExercise;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseResponse {

    private Long id;
    private Long testId;
    private String answer;
    private IExercise exercise;
}
