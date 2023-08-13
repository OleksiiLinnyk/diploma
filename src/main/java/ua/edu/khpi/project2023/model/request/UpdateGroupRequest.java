package ua.edu.khpi.project2023.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateGroupRequest {

    private Long groupId;
    private String newGroupName;
}
