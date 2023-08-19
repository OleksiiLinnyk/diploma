package ua.edu.khpi.project2023.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class PassExerciseRequest {

    @NotNull
    private Long exerciseId;
    @NotNull
    private String answer;
}
