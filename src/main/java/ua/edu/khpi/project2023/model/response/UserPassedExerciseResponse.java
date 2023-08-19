package ua.edu.khpi.project2023.model.response;

import lombok.*;
import ua.edu.khpi.project2023.exercise.model.IExercise;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserPassedExerciseResponse extends ExerciseResponse {

    private Long userId;
    private String givenAnswer;

    public UserPassedExerciseResponse(Long id, Long testId, String answer, IExercise exercise, Long userId, String givenAnswer) {
        super(id, testId, answer, exercise);
        this.userId = userId;
        this.givenAnswer = givenAnswer;
    }
}
