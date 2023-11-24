package ua.edu.khpi.project2023.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyResultsResponse {
    private Long testId;
    private String theme;
    private String subject;
    private String status;
    private int takenPoints;
    private int totalPoints;
}
