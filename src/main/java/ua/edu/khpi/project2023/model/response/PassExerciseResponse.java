package ua.edu.khpi.project2023.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.khpi.project2023.exercise.model.IExercise;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassExerciseResponse {
    private Long id;
    private Long testId;
    private IExercise exercise;
    private String givenAnswer;
    private Boolean checked;
    private Integer takenPoints;
}
