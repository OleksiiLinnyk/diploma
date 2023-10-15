package ua.edu.khpi.project2023.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
