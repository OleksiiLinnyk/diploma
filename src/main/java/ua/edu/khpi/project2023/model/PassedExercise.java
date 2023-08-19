package ua.edu.khpi.project2023.model;

import lombok.*;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PassedExercise extends Exercise {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "given_answer")
    private String givenAnswer;
}
