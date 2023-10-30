package ua.edu.khpi.project2023.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProgressResponse {
    private Long id;
    private String name;
    private String status;
    private int takenPoints;
    private int totalPoints;
}
