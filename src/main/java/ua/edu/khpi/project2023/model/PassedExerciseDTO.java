package ua.edu.khpi.project2023.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PassedExerciseDTO {

    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("exercise_id")
    private Long exercise_id;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("test_id")
    private Long testId;
    @JsonProperty("question")
    private String question;
    @JsonProperty("answer")
    private String answer;
    @JsonProperty("given_answer")
    private String givenAnswer;
    @JsonProperty("checked")
    private Boolean checked;
    @JsonProperty("taken_points")
    private Integer takenPoints;
}
