package ua.edu.khpi.project2023.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupsProgressResponse {
    private Long groupId;
    private String groupName;
    private int totalUsers;
    private int awaiting;
    private int toCheck;
    private int done;
}
