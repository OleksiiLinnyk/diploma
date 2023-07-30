package ua.edu.khpi.project2023.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseCreateRequest {

    private String answer;
    private Long testId;
    private ExerciseTypedRequest exerciseTypedRequest;
}
