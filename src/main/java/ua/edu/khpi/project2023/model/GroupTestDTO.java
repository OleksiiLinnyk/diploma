package ua.edu.khpi.project2023.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupTestDTO {
    private Long testId;
    private String subject;
    private String theme;
    private Long groupId;
    private String groupName;
}
